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

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.soletraderidentificationfrontend.config.AppConfig
import uk.gov.hmrc.soletraderidentificationfrontend.forms.CaptureSautrForm
import uk.gov.hmrc.soletraderidentificationfrontend.services.SautrStorageService
import uk.gov.hmrc.soletraderidentificationfrontend.views.html.capture_sautr_page

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CaptureSautrController @Inject()(mcc: MessagesControllerComponents,
                                       view: capture_sautr_page,
                                       sautrStorageService: SautrStorageService)
                                      (implicit val config: AppConfig, executionContext: ExecutionContext) extends FrontendController(mcc) {
  val name = "John Smith"

  def show(journeyId: String): Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(view(routes.CaptureSautrController.submit(journeyId), name, CaptureSautrForm.form))
      )
  }

  def submit(journeyId: String): Action[AnyContent] = Action.async {
    implicit request =>
      CaptureSautrForm.form.bindFromRequest().fold(
        formWithErrors => Future.successful(
          BadRequest(view(routes.CaptureSautrController.submit(journeyId), name, formWithErrors))
        ),
        sautr => sautrStorageService.storeSautr(journeyId, sautr).map {
          _ => Redirect(routes.CheckYourAnswersController.show(journeyId))
        }
      )
  }

}
