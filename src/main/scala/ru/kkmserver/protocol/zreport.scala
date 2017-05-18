package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class ZReportRequest (
  Command: String,
  NumDevice: Int,
  IdCommand: String = java.util.UUID.randomUUID().toString
)

object ZReportRequest {

  implicit val zReportRequestWrites: Writes[ZReportRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "NumDevice").write[Int] and
      (JsPath \ "IdCommand").write[String]
    )(unlift(ZReportRequest.unapply))

}

case class ZReportResponse (
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int,
  URL: String
)

object ZReportResponse {

  implicit val zReportResponseReads: Reads[ZReportResponse] = (
    (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String] and
      (JsPath \ "NumDevice").read[Int] and
      (JsPath \ "URL").read[String]
    )(ZReportResponse.apply _)

}
