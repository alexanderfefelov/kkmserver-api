package ru.kkmserver.protocol

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
  CashierName: String
)

object OpenShiftRequest {

  implicit val openShiftRequestWrites: Writes[OpenShiftRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").write[String]
    )(unlift(OpenShiftRequest.unapply))

}

case class OpenShiftResponse (
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

object OpenShiftResponse {

  implicit val openShiftResponseReads: Reads[OpenShiftResponse] = (
    ((__ \ "SessionNumber").read[Int] or Reads.pure(DEFAULT_SESSION_NUMBER)) and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(OpenShiftResponse.apply _)

}
