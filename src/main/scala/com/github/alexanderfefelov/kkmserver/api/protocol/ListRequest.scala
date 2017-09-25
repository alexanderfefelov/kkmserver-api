package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ListRequest (
  // Meta
  //
  Command: String = COMMAND_LIST,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Option[Int] = None,
  InnKkm: Option[String] = None,
  IP: Option[String] = None,
  Active: Option[Boolean] = None,
  OnOff: Option[Boolean] = None,
  OFD_Error: Option[Boolean] = None,
  OFD_DateErrorDoc: Option[DateTime] = None,
  FN_DateEnd: Option[DateTime] = None,
  FN_MemOverflowl: Option[Boolean] = None,
  FN_IsFiscal: Option[Boolean] = None
)

object ListRequest {

  implicit val listRequestWrites: Writes[ListRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "InnKkm").writeNullable[String] and
    (__ \ "IP").writeNullable[String] and
    (__ \ "Active").writeNullable[Boolean] and
    (__ \ "OnOff").writeNullable[Boolean] and
    (__ \ "OFD_Error").writeNullable[Boolean] and
    (__ \ "OFD_DateErrorDoc").writeNullable[DateTime] and
    (__ \ "FN_DateEnd").writeNullable[DateTime] and
    (__ \ "FN_MemOverflowl").writeNullable[Boolean] and
    (__ \ "FN_IsFiscal").writeNullable[Boolean]
    )(unlift(ListRequest.unapply))

}
