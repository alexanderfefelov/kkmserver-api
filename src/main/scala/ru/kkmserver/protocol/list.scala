package ru.kkmserver.protocol

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

case class ListUnit (
  NumDevice: Int,
  IdDevice: String,
  OnOff: Boolean,
  Active: Boolean,
  TypeDevice: String,
  IdTypeDevice: String,
  IP: String,
  NameDevice: String,
  KktNumber: String,
  INN: String,
  NameOrganization: String,
  TaxVariant: String,
  AddDate: DateTime,
  OFD_Error: String,
  OFD_NumErrorDoc: Int,
  OFD_DateErrorDoc: DateTime,
  FN_DateEnd: DateTime,
  FN_MemOverflowl: Boolean,
  FN_IsFiscal: Boolean,
  PaperOver: Boolean
)

case class ListResponse (
  // Payload
  //
  ListUnit: List[ListUnit],
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String
)

object ListResponse {

  implicit val listUnitReads: Reads[ListUnit] = (
    (__ \ "NumDevice").read[Int] and
    (__ \ "IdDevice").read[String] and
    (__ \ "OnOff").read[Boolean] and
    (__ \ "Active").read[Boolean] and
    (__ \ "TypeDevice").read[String] and
    (__ \ "IdTypeDevice").read[String] and
    (__ \ "IP").read[String] and
    (__ \ "NameDevice").read[String] and
    (__ \ "KktNumber").read[String] and
    (__ \ "INN").read[String] and
    (__ \ "NameOrganization").read[String] and
    (__ \ "TaxVariant").read[String] and
    (__ \ "AddDate").read[DateTime](jodaReadsLong) and
    (__ \ "OFD_Error").read[String] and
    (__ \ "OFD_NumErrorDoc").read[Int] and
    (__ \ "OFD_DateErrorDoc").read[DateTime](jodaReadsShort) and
    (__ \ "FN_DateEnd").read[DateTime](jodaReadsShort) and
    (__ \ "FN_MemOverflowl").read[Boolean] and
    (__ \ "FN_IsFiscal").read[Boolean] and
    (__ \ "PaperOver").read[Boolean]
    )(ListUnit.apply _)

  implicit val listResponseReads: Reads[ListResponse] = (
    (__ \ "ListUnit").read[List[ListUnit]] and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String]
    )(ListResponse.apply _)

}
