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
  CashierName: Option[String] = None
)

object OpenShiftRequest {

  implicit val openShiftRequestWrites: Writes[OpenShiftRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "CashierName").writeNullable[String]
    )(unlift(OpenShiftRequest.unapply))

}

case class OpenShiftResponse (
  // Payload
  //
  SessionNumber: Int,
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object OpenShiftResponse {

  implicit val openShiftResponseReads: Reads[OpenShiftResponse] = (
    (__ \ "SessionNumber").read[Int] and
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(OpenShiftResponse.apply _)

}
