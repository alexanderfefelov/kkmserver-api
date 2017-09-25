package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class PaymentCashResponse (
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object PaymentCashResponse {

  implicit val paymentCashResponseReads: Reads[PaymentCashResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(PaymentCashResponse.apply _)

}
