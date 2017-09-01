package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class PaymentCashRequest (
  // Meta
  //
  Command: String = COMMAND_PAYMENT_CASH,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  Amount: Double
)

object PaymentCashRequest {

  implicit val paymentCashRequestWrites: Writes[PaymentCashRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "Amount").write[Double]
    )(unlift(PaymentCashRequest.unapply))

}

case class PaymentCashResponse (
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int
)

object PaymentCashResponse {

  implicit val paymentCashResponseReads: Reads[PaymentCashResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int]
    )(PaymentCashResponse.apply _)

}
