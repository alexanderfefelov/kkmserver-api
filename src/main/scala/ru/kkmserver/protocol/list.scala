package ru.kkmserver.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class ListRequest (
  Command: String,
  NumDevice: Option[Int],
  InnKkm: Option[String],
  IP: Option[String],
  Active: Option[Boolean],
  OnOff: Option[Boolean],
  OFD_Error: Option[Boolean],
  OFD_DateErrorDoc: Option[DateTime],
  FN_DateEnd: Option[DateTime],
  FN_MemOverflowl: Option[Boolean],
  FN_IsFiscal: Option[Boolean],
  IdCommand: String = java.util.UUID.randomUUID().toString
)

object ListRequest {

  val Command = "List"

  implicit val listRequestWrites: Writes[ListRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "NumDevice").writeNullable[Int] and
      (JsPath \ "InnKkm").writeNullable[String] and
      (JsPath \ "IP").writeNullable[String] and
      (JsPath \ "Active").writeNullable[Boolean] and
      (JsPath \ "OnOff").writeNullable[Boolean] and
      (JsPath \ "OFD_Error").writeNullable[Boolean] and
      (JsPath \ "OFD_DateErrorDoc").writeNullable[DateTime] and
      (JsPath \ "FN_DateEnd").writeNullable[DateTime] and
      (JsPath \ "FN_MemOverflowl").writeNullable[Boolean] and
      (JsPath \ "FN_IsFiscal").writeNullable[Boolean] and
      (JsPath \ "IdCommand").write[String]
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
  ListUnit: List[ListUnit],
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String
)

object ListResponse {

  implicit val listUnitReads: Reads[ListUnit] = (
    (JsPath \ "NumDevice").read[Int] and
      (JsPath \ "IdDevice").read[String] and
      (JsPath \ "OnOff").read[Boolean] and
      (JsPath \ "Active").read[Boolean] and
      (JsPath \ "TypeDevice").read[String] and
      (JsPath \ "IdTypeDevice").read[String] and
      (JsPath \ "IP").read[String] and
      (JsPath \ "NameDevice").read[String] and
      (JsPath \ "KktNumber").read[String] and
      (JsPath \ "INN").read[String] and
      (JsPath \ "NameOrganization").read[String] and
      (JsPath \ "TaxVariant").read[String] and
      (JsPath \ "AddDate").read[DateTime](jodaReadsLong) and
      (JsPath \ "OFD_Error").read[String] and
      (JsPath \ "OFD_NumErrorDoc").read[Int] and
      (JsPath \ "OFD_DateErrorDoc").read[DateTime](jodaReadsShort) and
      (JsPath \ "FN_DateEnd").read[DateTime](jodaReadsShort) and
      (JsPath \ "FN_MemOverflowl").read[Boolean] and
      (JsPath \ "FN_IsFiscal").read[Boolean] and
      (JsPath \ "PaperOver").read[Boolean]
    )(ListUnit.apply _)

  implicit val listResponseReads: Reads[ListResponse] = (
    (JsPath \ "ListUnit").read[List[ListUnit]] and
      (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String]
    )(ListResponse.apply _)

}
