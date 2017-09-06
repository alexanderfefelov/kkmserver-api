package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class XReportRequest (
  // Meta
  //
  Command: String = COMMAND_X_REPORT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int
)

object XReportRequest {

  implicit val xReportRequestWrites: Writes[XReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int]
    )(unlift(XReportRequest.unapply))

}

case class XReportResponse (
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object XReportResponse {

  implicit val xReportResponseReads: Reads[XReportResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(XReportResponse.apply _)

}
