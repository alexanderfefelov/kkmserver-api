package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class RegisterCheckResponse10 (
  // Payload
  //
  SessionNumber: Int,
  CheckNumber: Int,
  URL: String,
  QRCode: String,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object RegisterCheckResponse10 {

  implicit val registerCheckResponseReads10: Reads[RegisterCheckResponse10] = (
    ((__ \ "SessionNumber").read[Int] or Reads.pure(DEFAULT_SESSION_NUMBER)) and
    ((__ \ "CheckNumber").read[Int] or Reads.pure(DEFAULT_CHECK_NUMBER)) and
    ((__ \ "URL").read[String] or Reads.pure(DEFAULT_URL)) and
    ((__ \ "QRCode").read[String] or Reads.pure(DEFAULT_QRCODE)) and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(RegisterCheckResponse10.apply _)

}
