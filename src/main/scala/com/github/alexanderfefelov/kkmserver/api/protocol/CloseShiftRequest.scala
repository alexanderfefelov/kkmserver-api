package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CloseShiftRequest (
  // Meta
  //
  Command: String = COMMAND_CLOSE_SHIFT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  CashierName: String,
  CashierVATIN: String
)

object CloseShiftRequest {

  implicit val closeShiftRequestWrites: Writes[CloseShiftRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String] and
    (__ \ "CashierVATIN").write[String]
    )(unlift(CloseShiftRequest.unapply))

}
