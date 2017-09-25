package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CloseShiftResponse (
  // Payload
  //
  SessionNumber: Int,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object CloseShiftResponse {

  implicit val closeShiftResponseReads: Reads[CloseShiftResponse] = (
    ((__ \ "SessionNumber").read[Int] or Reads.pure(DEFAULT_SESSION_NUMBER)) and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(CloseShiftResponse.apply _)

}
