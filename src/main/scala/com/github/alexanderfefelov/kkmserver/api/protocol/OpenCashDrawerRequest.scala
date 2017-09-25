package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OpenCashDrawerRequest (
  // Meta
  //
  Command: String = COMMAND_OPEN_CASH_DRAWER,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  CashierName: String
)

object OpenCashDrawerRequest {

  implicit val openCashDrawerRequestWrites: Writes[OpenCashDrawerRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String]
    )(unlift(OpenCashDrawerRequest.unapply))

}
