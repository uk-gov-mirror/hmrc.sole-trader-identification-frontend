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

import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.soletraderidentificationfrontend.config.AppConfig
import uk.gov.hmrc.soletraderidentificationfrontend.forms.CaptureDateOfBirthForm
import uk.gov.hmrc.soletraderidentificationfrontend.services.SoleTraderIdentificationService
import uk.gov.hmrc.soletraderidentificationfrontend.views.html.capture_date_of_birth_page

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CaptureDateOfBirthController @Inject()(mcc: MessagesControllerComponents,
                                             view: capture_date_of_birth_page,
                                             captureDateOfBirthForm: CaptureDateOfBirthForm,
                                             soleTraderIdentificationService: SoleTraderIdentificationService
                                            )(implicit appConfig: AppConfig,
                                              ec: ExecutionContext) extends FrontendController(mcc) {

  def show(journeyId: String): Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(view(routes.CaptureDateOfBirthController.submit(journeyId), captureDateOfBirthForm.apply())))
  }

  def submit(journeyId: String): Action[AnyContent] = Action.async {
    implicit request =>
      captureDateOfBirthForm.apply().bindFromRequest().fold(
        formWithErrors =>
          Future.successful(
            BadRequest(view(routes.CaptureDateOfBirthController.submit(journeyId), formWithErrors))
          ),
        dateOfBirth =>
          soleTraderIdentificationService.storeDateOfBirth(journeyId, dateOfBirth).map {
            _ => Redirect(routes.CaptureNinoController.show(journeyId))
          }
      )
  }
}