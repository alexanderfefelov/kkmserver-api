package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class XReportRequest (
  Command: String = CommandXReport,
  NumDevice: Int,
  IdCommand: String = java.util.UUID.randomUUID().toString
)

object XReportRequest {

  implicit val xReportRequestWrites: Writes[XReportRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "NumDevice").write[Int] and
      (JsPath \ "IdCommand").write[String]
    )(unlift(XReportRequest.unapply))

}

case class XReportResponse (
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int,
  URL: String
)

object XReportResponse {

  implicit val xReportResponseReads: Reads[XReportResponse] = (
    (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String] and
      (JsPath \ "NumDevice").read[Int] and
      (JsPath \ "URL").read[String]
    )(XReportResponse.apply _)

}
