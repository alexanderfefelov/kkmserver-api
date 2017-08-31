package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OfdReportRequest (
  // Meta
  //
  Command: String = COMMAND_OFD_REPORT,
  NumDevice: Int,
  IdCommand: String = createUuid
)

object OfdReportRequest {

  implicit val ofdReportRequestWrites: Writes[OfdReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "IdCommand").write[String]
    )(unlift(OfdReportRequest.unapply))

}

case class OfdReportResponse (
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object OfdReportResponse {

  implicit val ofdReportReads: Reads[OfdReportResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(OfdReportResponse.apply _)

}
