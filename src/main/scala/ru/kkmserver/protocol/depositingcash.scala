package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class DepositingCashRequest (
  // Meta
  //
  Command: String = CommandDepositingCash,
  NumDevice: Option[Int] = None,
  IdCommand: String = createUuid,
  // Payload
  //
  Amount: Double
)

object DepositingCashRequest {

  implicit val depositingCashRequestWrites: Writes[DepositingCashRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "IdCommand").write[String] and
    (__ \ "Amount").write[Double]
    )(unlift(DepositingCashRequest.unapply))

}

case class DepositingCashResponse (
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object DepositingCashResponse {

  implicit val depositingCashResponseReads: Reads[DepositingCashResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(DepositingCashResponse.apply _)

}
