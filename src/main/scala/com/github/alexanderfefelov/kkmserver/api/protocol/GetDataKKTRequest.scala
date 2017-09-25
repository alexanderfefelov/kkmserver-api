package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetDataKKTRequest (
  // Meta
  //
  Command: String = COMMAND_GET_DATA_KKT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int
)

object GetDataKKTRequest {

  implicit val getDataKKTRequestWrites: Writes[GetDataKKTRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int]
    )(unlift(GetDataKKTRequest.unapply))

}
