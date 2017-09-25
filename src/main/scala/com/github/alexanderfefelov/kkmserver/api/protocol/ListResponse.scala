package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ListUnit (
  NumDevice: Int,
  IdDevice: String,
  OnOff: Boolean,
  Active: Boolean,
  TypeDevice: String,
  IdTypeDevice: String,
  IP: String,
  NameDevice: String,
  UnitName: String,
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
    (__ \ "UnitName").read[String] and
    (__ \ "KktNumber").read[String] and
    (__ \ "INN").read[String] and
    (__ \ "NameOrganization").read[String] and
    (__ \ "TaxVariant").read[String] and
    (__ \ "AddDate").read[DateTime](jodaReads) and
    (__ \ "OFD_Error").read[String] and
    (__ \ "OFD_NumErrorDoc").read[Int] and
    (__ \ "OFD_DateErrorDoc").read[DateTime](jodaReads) and
    (__ \ "FN_DateEnd").read[DateTime](jodaReads) and
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
