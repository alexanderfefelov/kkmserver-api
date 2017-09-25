package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OpenShiftRequest (
  // Meta
  //
  Command: String = COMMAND_OPEN_SHIFT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  CashierName: String,
  CashierVATIN: String
)

object OpenShiftRequest {

  implicit val openShiftRequestWrites: Writes[OpenShiftRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String] and
    (__ \ "CashierVATIN").write[String]
    )(unlift(OpenShiftRequest.unapply))

}
