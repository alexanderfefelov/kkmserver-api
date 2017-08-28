package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ZReportRequest (
  // Meta
  //
  Command: String = CommandZReport,
  NumDevice: Option[Int] = None,
  IdCommand: String = createUuid
)

object ZReportRequest {

  implicit val zReportRequestWrites: Writes[ZReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "IdCommand").write[String]
    )(unlift(ZReportRequest.unapply))

}

case class ZReportResponse (
  // Payload
  //
  SessionNumber: Int,
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object ZReportResponse {

  implicit val zReportResponseReads: Reads[ZReportResponse] = (
    (__ \ "SessionNumber").read[Int] and
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(ZReportResponse.apply _)

}
