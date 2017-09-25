package com.github.alexanderfefelov.kkmserver.api.protocol

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
  CashierName: String,
  CashierVATIN: String
)

object ZReportRequest {

  implicit val zReportRequestWrites: Writes[ZReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String] and
    (__ \ "CashierVATIN").write[String]
    )(unlift(ZReportRequest.unapply))

}
