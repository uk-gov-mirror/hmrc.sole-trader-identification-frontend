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

package uk.gov.hmrc.soletraderidentificationfrontend.assets

object MessageLookup {

  object Base {
    val confirmAndContinue = "Confirm and continue"
    val change = "Change"
    val saveAndContinue = "Save and continue"
    val getHelp = "Is this page not working properly?"

    object Error {
      val title = "There is a problem"
      val error = "Error: "
    }

  }

  object Header {
    val signOut = "Sign out"
  }

  object BetaBanner {
    val title = "This is a new service - your feedback (opens in new tab) will help us to improve it."
  }

  object CaptureFullName {
    val title = "What is your full name?"
    val heading = "What is your full name?"
    val line_1 = "We will attempt to match these details with the information we already have."
    val form_field_1 = "First name"
    val form_field_2 = "Last name"

    object Error {
      val noFirstNameEntered = "Enter your first name"
      val noLastNameEntered = "Enter your last name"
      val invalidFirstNameEntered = "First name must be 99 characters or fewer."
      val invalidLastNameEntered = "Last name must be 99 characters or fewer."
    }

  }

  object CaptureDateOfBirth {
    val title = "What is your date of birth?"
    val heading = "What is your date of birth?"
    val hint = "For example, 27 3 2007"

    object Error {
      val noDobEntered = "Enter your date of birth"
      val invalidDate =  "Enter a real date"
      val futureDate = "The date of birth must be in the past"
      val invalidAge = "You must be at least 16 years of age"
    }

  }

  object CaptureNino {
    val title = "What is your National Insurance number?"
    val heading = "What is John Smith’s National Insurance number?"
    val line_1 = "It’s on the National Insurance card, benefit letter, payslip or P60. For example, ‘QQ 12 34 56 C’."
    val form_field_1 = "It’s on the National Insurance card, benefit letter, payslip or P60. For example, ‘QQ 12 34 56 C’."

    object Error {
      val invalidNinoEntered = "Enter a National Insurance number in the correct format"
    }

  }

  object CaptureSautr {
    val title = "What is John Smith’s Unique Taxpayer Reference?"
    val heading = "What is John Smith’s Unique Taxpayer Reference?"
    val line_1 = "This is 10 numbers, for example 1234567890. It will be on tax returns and other letters about Self Assessment. It may be called ‘reference’, ‘UTR’ or ‘official use’."
    val line_2 = "John Smith does not have a Unique Taxpayer Reference"
    val details_line_1 = "Your UTR helps us identify your business"
    val details_line_2 = "I cannot find the UTR"
    val details_line_3 = "The business does not have a UTR"

    object Error {
      val invalidSautrEntered = "Enter a Unique Taxpayer Reference in the correct format"
    }

  }

  object PersonalInformationError {
    val title = "We could not identify you on our records"
    val heading = "We could not identify you on our records"
    val line_1 = "This could have been because of a mistake when entering your name, date of birth or National Insurance number."
    val button = "Try again"
  }

  object CheckYourAnswers {
    val title = "Check your answers"
    val heading = "Check your answers"
    val firstName = "First name"
    val lastName = "Last name"
    val dob = "Date of birth"
    val nino = "National insurance number"
    val sautr = "Unique taxpayers reference number"
    val noSautr = "The business does not have a UTR"
  }

}
