package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetDataCheckRequest (
  // Meta
  //
  Command: String = COMMAND_GET_DATA_CHECK,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int,
  // Check selector
  //
  FiscalNumber: Int,
  // Payload
  //
  NumberCopies: Int = 0
)

object GetDataCheckRequest {

  implicit val getDataCheckRequestWrites: Writes[GetDataCheckRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int] and
    (__ \ "FiscalNumber").write[Int] and
    (__ \ "NumberCopies").write[Int]
    )(unlift(GetDataCheckRequest.unapply))

}
