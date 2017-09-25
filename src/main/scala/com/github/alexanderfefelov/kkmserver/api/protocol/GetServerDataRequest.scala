package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetServerDataRequest (
  // Meta
  //
  Command: String = COMMAND_GET_SERVER_DATA,
  IdCommand: String = createUuid
)

object GetServerDataRequest {

  implicit val getServerDataRequestWrites: Writes[GetServerDataRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String]
    )(unlift(GetServerDataRequest.unapply))

}
