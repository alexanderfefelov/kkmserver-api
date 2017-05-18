package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class GetRezultRequest (
  Command: String = CommandGetRezult,
  IdCommand: String
)

object GetRezultRequest {

  implicit val getRezultRequestWrites: Writes[GetRezultRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "IdCommand").write[String]
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
    (JsPath \ "SessionNumber").read[Int] and
      (JsPath \ "URL").read[String] and
      (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String] and
      (JsPath \ "NumDevice").read[Int]
    )(RezultData.apply _)

  implicit val getRezultResponseReads: Reads[GetRezultResponse] = (
    (JsPath \ "Rezult").read[RezultData] and
      (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String]
    )(GetRezultResponse.apply _)

}
