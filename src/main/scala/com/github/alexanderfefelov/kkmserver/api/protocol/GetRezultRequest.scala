package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetRezultRequest (
  // Meta
  //
  Command: String = COMMAND_GET_REZULT,
  // Payload
  //
  IdCommand: String
)

object GetRezultRequest {

  implicit val getRezultRequestWrites: Writes[GetRezultRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String]
    )(unlift(GetRezultRequest.unapply))

}
