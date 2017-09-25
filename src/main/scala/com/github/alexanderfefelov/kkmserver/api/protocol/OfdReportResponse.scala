package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OfdReportResponse (
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object OfdReportResponse {

  implicit val ofdReportReads: Reads[OfdReportResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(OfdReportResponse.apply _)

}
