package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class MinimalResponse (
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String
)

object MinimalResponse {

  implicit val minimalResponseReads: Reads[MinimalResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String]
    )(MinimalResponse.apply _)

}
