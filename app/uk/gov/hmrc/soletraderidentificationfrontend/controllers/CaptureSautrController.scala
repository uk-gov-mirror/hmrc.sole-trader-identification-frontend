/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.soletraderidentificationfrontend.controllers

import play.api.mvc._
import uk.gov.hmrc.auth.core.{AuthConnector, AuthorisedFunctions}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.soletraderidentificationfrontend.config.AppConfig
import uk.gov.hmrc.soletraderidentificationfrontend.forms.CaptureSautrForm
import uk.gov.hmrc.soletraderidentificationfrontend.services.{JourneyService, SoleTraderIdentificationService}
import uk.gov.hmrc.soletraderidentificationfrontend.views.html.capture_sautr_page

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class CaptureSautrController @Inject()(mcc: MessagesControllerComponents,
                                       view: capture_sautr_page,
                                       soleTraderIdentificationService: SoleTraderIdentificationService,
                                       val authConnector: AuthConnector,
                                       journeyService: JourneyService
                                      )(implicit val config: AppConfig,
                                        executionContext: ExecutionContext) extends FrontendController(mcc) with AuthorisedFunctions {
  val name = "John Smith" // TODO this will be pre-pop data

  def show(journeyId: String): Action[AnyContent] = Action.async {
    implicit request =>
      authorised() {
        journeyService.getJourneyConfig(journeyId).map {
          journeyConfig =>
            Ok(view(
              pageConfig = journeyConfig.pageConfig,
              formAction = routes.CaptureSautrController.submit(journeyId),
              personsName = name,
              form = CaptureSautrForm.form
            ))
        }
      }
  }

  def submit(journeyId: String): Action[AnyContent] = Action.async {
    implicit request =>
      CaptureSautrForm.form.bindFromRequest().fold(
        formWithErrors =>
          journeyService.getJourneyConfig(journeyId).map {
            journeyConfig =>
              BadRequest(view(
                pageConfig = journeyConfig.pageConfig,
                formAction = routes.CaptureSautrController.submit(journeyId),
                personsName = name,
                form = formWithErrors
              ))
          },
        sautr =>
          soleTraderIdentificationService.storeSautr(journeyId, sautr).map {
            _ => Redirect(routes.CheckYourAnswersController.show(journeyId))
          }
      )
  }

}
