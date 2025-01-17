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

import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.soletraderidentificationfrontend.assets.TestConstants._
import uk.gov.hmrc.soletraderidentificationfrontend.stubs.{AuthStub, SoleTraderIdentificationStub}
import uk.gov.hmrc.soletraderidentificationfrontend.utils.ComponentSpecHelper
import uk.gov.hmrc.soletraderidentificationfrontend.views.CaptureNinoViewTests

class CaptureNinoControllerISpec extends ComponentSpecHelper
  with CaptureNinoViewTests
  with SoleTraderIdentificationStub
  with AuthStub {

  "GET /national-insurance-number" should {
    lazy val result = {
      await(insertJourneyConfig(
        journeyId = testJourneyId,
        continueUrl = testContinueUrl,
        optServiceName = None,
        deskProServiceId = testDeskProServiceId,
        signOutUrl = testSignOutUrl
      ))
      stubAuth(OK, successfulAuthResponse())
      get(s"/identify-your-sole-trader-business/$testJourneyId/national-insurance-number")
    }

    "return OK" in {
      result.status mustBe OK
    }

    "return a view which" should {
      testCaptureNinoView(result)
    }

    "redirect to sign in page" when {
      "the user is UNAUTHORISED" in {
        stubAuthFailure()
        lazy val result: WSResponse = get(s"/identify-your-sole-trader-business/$testJourneyId/national-insurance-number")

        result must have(
          httpStatus(SEE_OTHER),
          redirectUri("/bas-gateway/sign-in" +
            s"?continue_url=%2Fidentify-your-sole-trader-business%2F$testJourneyId%2Fnational-insurance-number" +
            "&origin=sole-trader-identification-frontend"
          )
        )
      }
    }
  }

  "POST /national-insurance-number" should {
    "redirect to the capture sautr page" in {
      await(insertJourneyConfig(
        journeyId = testJourneyId,
        continueUrl = testContinueUrl,
        optServiceName = None,
        deskProServiceId = testDeskProServiceId,
        signOutUrl = testSignOutUrl
      ))
      stubAuth(OK, successfulAuthResponse())
      stubStoreNino(testJourneyId, testNino)(status = OK)

      lazy val result = post(s"/identify-your-sole-trader-business/$testJourneyId/national-insurance-number")("nino" -> testNino)

      result must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.CaptureSautrController.show(testJourneyId).url)
      )
    }
  }

  "no nino is submitted" should {
    lazy val result = {
      await(insertJourneyConfig(
        journeyId = testJourneyId,
        continueUrl = testContinueUrl,
        optServiceName = None,
        deskProServiceId = testDeskProServiceId,
        signOutUrl = testSignOutUrl
      ))
      stubAuth(OK, successfulAuthResponse())
      post(s"/identify-your-sole-trader-business/$testJourneyId/national-insurance-number")("nino" -> "")
    }

    "return a bad request" in {
      result.status mustBe BAD_REQUEST
    }

    testCaptureNinoErrorMessages(result)
  }

  "an invalid nino is submitted" should {
    lazy val result = {
      await(insertJourneyConfig(
        journeyId = testJourneyId,
        continueUrl = testContinueUrl,
        optServiceName = None,
        deskProServiceId = testDeskProServiceId,
        signOutUrl = testSignOutUrl
      ))
      stubAuth(OK, successfulAuthResponse())
      post(s"/identify-your-sole-trader-business/$testJourneyId/national-insurance-number")("nino" -> "AAAAAAAAAA")
    }

    "return a bad request" in {
      result.status mustBe BAD_REQUEST
    }

    testCaptureNinoErrorMessages(result)
  }

}
