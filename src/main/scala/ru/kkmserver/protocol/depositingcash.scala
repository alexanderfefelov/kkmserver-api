package ru.kkmserver.protocol

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
  Amount: Double
)

object DepositingCashRequest {

  implicit val depositingCashRequestWrites: Writes[DepositingCashRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "Amount").write[Double]
    )(unlift(DepositingCashRequest.unapply))

}

case class DepositingCashResponse (
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int
)

object DepositingCashResponse {

  implicit val depositingCashResponseReads: Reads[DepositingCashResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int]
    )(DepositingCashResponse.apply _)

}
