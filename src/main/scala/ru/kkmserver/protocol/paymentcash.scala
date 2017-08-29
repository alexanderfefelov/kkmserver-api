package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class PaymentCashRequest (
  // Meta
  //
  Command: String = CommandPaymentCash,
  NumDevice: Option[Int] = None,
  IdCommand: String = createUuid,
  // Payload
  //
  Amount: Double
)

object PaymentCashRequest {

  implicit val paymentCashRequestWrites: Writes[PaymentCashRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "IdCommand").write[String] and
    (__ \ "Amount").write[Double]
    )(unlift(PaymentCashRequest.unapply))

}

case class PaymentCashResponse (
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object PaymentCashResponse {

  implicit val paymentCashResponseReads: Reads[PaymentCashResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(PaymentCashResponse.apply _)

}
