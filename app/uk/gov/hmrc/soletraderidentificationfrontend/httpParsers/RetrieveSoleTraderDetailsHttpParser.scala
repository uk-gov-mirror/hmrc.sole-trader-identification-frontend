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

package uk.gov.hmrc.soletraderidentificationfrontend.httpParsers

import play.api.http.Status.{OK, NOT_FOUND}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.http.{HttpReads, HttpResponse, InternalServerException}
import uk.gov.hmrc.soletraderidentificationfrontend.models.SoleTraderDetails

import java.time.LocalDate

object RetrieveSoleTraderDetailsHttpParser {

  implicit object RetrieveSoleTraderDetailsHttpReads extends HttpReads[Option[SoleTraderDetails]] {
    override def read(method: String, url: String, response: HttpResponse): Option[SoleTraderDetails] = {
      response.status match {
        case OK =>
          response.json.validate[SoleTraderDetails](soleTraderDetailsReads) match {
            case JsSuccess(soleTraderDetails, _) => Some(soleTraderDetails)
            case JsError(errors) =>
              throw new InternalServerException(s"`Failed to read Sole Trader Details with the following error/s: $errors")
          }
        case NOT_FOUND =>
          None
        case status =>
          throw new InternalServerException(s"Unexpected status from Sole Trader Details retrieval. Status returned - $status")
      }
    }
  }

  private val FullNameKey = "fullName"
  private val FirstNameKey = "firstName"
  private val LastNameKey = "lastName"
  private val NinoKey = "nino"
  private val SautrKey = "sautr"
  private val DateOfBirthKey = "dateOfBirth"

  val soleTraderDetailsReads: Reads[SoleTraderDetails] = (
    (JsPath \ FullNameKey \ FirstNameKey).read[String] and
      (JsPath \ FullNameKey \ LastNameKey).read[String] and
      (JsPath \ DateOfBirthKey).read[LocalDate] and
      (JsPath \ NinoKey).read[String] and
      (JsPath \ SautrKey).readNullable[String]
    ) (SoleTraderDetails.apply _)

}
