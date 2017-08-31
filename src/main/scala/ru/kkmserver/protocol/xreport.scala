package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class XReportRequest (
  // Meta
  //
  Command: String = CommandXReport,
  NumDevice: Int,
  IdCommand: String = createUuid
)

object XReportRequest {

  implicit val xReportRequestWrites: Writes[XReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "IdCommand").write[String]
    )(unlift(XReportRequest.unapply))

}

case class XReportResponse (
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object XReportResponse {

  implicit val xReportResponseReads: Reads[XReportResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(XReportResponse.apply _)

}
