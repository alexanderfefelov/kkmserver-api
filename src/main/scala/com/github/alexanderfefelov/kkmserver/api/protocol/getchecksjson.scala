package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetChecksJSONRequest (
  // Meta
  //
  Command: String = COMMAND_GET_CHECKS_JSON,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int
)

object GetChecksJSONRequest {

  implicit val getChecksJSONRequestWrites: Writes[GetChecksJSONRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int]
    )(unlift(GetChecksJSONRequest.unapply))

}

case class CheckData (
  FiscalNumber: String,
  FiscalDate: DateTime,
  FiscalSign: String,
  Summ: Double
)

case class GetChecksJSONResponse (
  // Payload
  //
  Checks: List[CheckData],
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object GetChecksJSONResponse {

  implicit val checkDataReads: Reads[CheckData] = (
    (__ \ "FiscalNumber").read[String] and
    (__ \ "FiscalDate").read[DateTime](jodaReads) and
    (__ \ "FiscalSign").read[String] and
    (__ \ "Summ").read[Double]
    )(CheckData.apply _)

  implicit val getChecksJSONResponseReads: Reads[GetChecksJSONResponse] = (
    (__ \ "Checks").read[List[CheckData]] and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(GetChecksJSONResponse.apply _)

}
