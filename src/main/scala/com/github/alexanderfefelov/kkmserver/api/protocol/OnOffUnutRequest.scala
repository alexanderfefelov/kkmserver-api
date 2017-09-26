package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OnOffUnutRequest (
  // Meta
  //
  Command: String = COMMAND_ON_OFF_UNUT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Payload
  //
  Active: Boolean
)

object OnOffUnutRequest {

  implicit val onOffUnutRequestWrites: Writes[OnOffUnutRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "Active").write[Boolean]
    )(unlift(OnOffUnutRequest.unapply))

}
