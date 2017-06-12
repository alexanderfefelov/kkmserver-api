package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetRezultRequest (
  Command: String = CommandGetRezult,
  IdCommand: String
)

object GetRezultRequest {

  implicit val getRezultRequestWrites: Writes[GetRezultRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String]
    )(unlift(GetRezultRequest.unapply))

}

case class RezultData (
  SessionNumber: Int,
  URL: String,
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

case class GetRezultResponse (
  Rezult: RezultData,
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String
)

object GetRezultResponse {

  implicit val rezultDataReads: Reads[RezultData] = (
    (__ \ "SessionNumber").read[Int] and
    (__ \ "URL").read[String] and
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(RezultData.apply _)

  implicit val getRezultResponseReads: Reads[GetRezultResponse] = (
    (__ \ "Rezult").read[RezultData] and
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String]
    )(GetRezultResponse.apply _)

}
