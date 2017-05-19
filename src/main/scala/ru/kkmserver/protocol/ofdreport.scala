package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class OfdReportRequest (
  Command: String = CommandOfdReport,
  NumDevice: Int,
  IdCommand: String = createUuid
)

object OfdReportRequest {

  implicit val ofdReportRequestWrites: Writes[OfdReportRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "NumDevice").write[Int] and
      (JsPath \ "IdCommand").write[String]
    )(unlift(OfdReportRequest.unapply))

}

case class OfdReportResponse (
  URL: String,
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object OfdReportResponse {

  implicit val ofdReportReads: Reads[OfdReportResponse] = (
    (JsPath \ "URL").read[String] and
      (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String] and
      (JsPath \ "NumDevice").read[Int]
    )(OfdReportResponse.apply _)

}
