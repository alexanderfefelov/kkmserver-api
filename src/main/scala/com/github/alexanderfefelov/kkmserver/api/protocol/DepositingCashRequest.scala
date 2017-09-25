package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class DepositingCashRequest (
  // Meta
  //
  Command: String = COMMAND_DEPOSITING_CASH,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  CashierName: String,
  CashierVATIN: String,
  Amount: Double
)

object DepositingCashRequest {

  implicit val depositingCashRequestWrites: Writes[DepositingCashRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String] and
    (__ \ "CashierVATIN").write[String] and
    (__ \ "Amount").write[Double]
    )(unlift(DepositingCashRequest.unapply))

}
