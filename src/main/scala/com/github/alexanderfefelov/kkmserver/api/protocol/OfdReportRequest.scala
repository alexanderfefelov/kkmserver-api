package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OfdReportRequest (
  // Meta
  //
  Command: String = COMMAND_OFD_REPORT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  CashierName: String
)

object OfdReportRequest {

  implicit val ofdReportRequestWrites: Writes[OfdReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String]
    )(unlift(OfdReportRequest.unapply))

}
