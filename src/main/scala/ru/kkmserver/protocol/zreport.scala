package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ZReportRequest (
  // Meta
  //
  Command: String = COMMAND_Z_REPORT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  CashierName: Option[String] = None
)

object ZReportRequest {

  implicit val zReportRequestWrites: Writes[ZReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").writeNullable[String]
    )(unlift(ZReportRequest.unapply))

}

case class ZReportResponse (
  // Payload
  //
  SessionNumber: Int,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int
)

object ZReportResponse {

  implicit val zReportResponseReads: Reads[ZReportResponse] = (
    (__ \ "SessionNumber").read[Int] and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int]
    )(ZReportResponse.apply _)

}
