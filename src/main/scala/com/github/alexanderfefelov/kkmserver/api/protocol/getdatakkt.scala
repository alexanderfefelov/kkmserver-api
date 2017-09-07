package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
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

case class OrganizationData (
  NameOrganization: String,
  InnOrganization: String,
  TaxVariant: String,
  SenderEmail: String
)

case class FFDData (
  FFDVersion: String,
  FFDVersionFN: String,
  FFDVersionKKT: String
)

case class FNData (
  FN_IsFiscal: Boolean,
  FN_MemOverflowl: Boolean,
  FN_DateEnd: DateTime
)

case class NumbersData (
  KktNumber: String,
  FnNumber: String,
  RegNumber: String
)

case class SettleData (
  AddressSettle: String,
  PlaceSettle: String
)

case class InfoData (
  Organization: OrganizationData,
  FFD: FFDData,
  FN: FNData,
  Numbers: NumbersData,
  Settle: SettleData,
  SessionState: Int,
  BalanceCash: Double,
  OnOff: Boolean,
  Active: Boolean,
  PaperOver: Boolean,
  LicenseExpirationDate: DateTime
)

case class GetDataKKTResponse (
  // Payload
  //
  SessionNumber: Int,
  CheckNumber: Int,
  Info: InfoData,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object GetDataKKTResponse {

  implicit val organizationDataReads: Reads[OrganizationData] = (
    (__ \ "NameOrganization").read[String] and
    (__ \ "InnOrganization").read[String] and
    (__ \ "TaxVariant").read[String] and
    (__ \ "SenderEmail").read[String]
    )(OrganizationData.apply _)

  implicit val ffdDataReads: Reads[FFDData] = (
    (__ \ "FFDVersion").read[String] and
    (__ \ "FFDVersionFN").read[String] and
    (__ \ "FFDVersionKKT").read[String]
    )(FFDData.apply _)

  implicit val fnDataReads: Reads[FNData] = (
    (__ \ "FN_IsFiscal").read[Boolean] and
    (__ \ "FN_MemOverflowl").read[Boolean] and
    (__ \ "FN_DateEnd").read[DateTime](jodaReads)
    )(FNData.apply _)

  implicit val numbersDataReads: Reads[NumbersData] = (
    (__ \ "KktNumber").read[String] and
    (__ \ "FnNumber").read[String] and
    (__ \ "RegNumber").read[String]
    )(NumbersData.apply _)

  implicit val settleDataReads: Reads[SettleData] = (
    (__ \ "AddressSettle").read[String] and
    (__ \ "PlaceSettle").read[String]
    )(SettleData.apply _)

  implicit val infoDataReads: Reads[InfoData] = (
    (__).read[OrganizationData] and
    (__).read[FFDData] and
    (__).read[FNData] and
    (__).read[NumbersData] and
    (__).read[SettleData] and
    (__ \ "SessionState").read[Int] and
    (__ \ "BalanceCash").read[Double] and
    (__ \ "OnOff").read[Boolean] and
    (__ \ "Active").read[Boolean] and
    (__ \ "PaperOver").read[Boolean] and
    (__ \ "LicenseExpirationDate").read[DateTime](jodaReads)
    )(InfoData.apply _)

  implicit val getDataKKTResponseReads: Reads[GetDataKKTResponse] = (
    ((__ \ "SessionNumber").read[Int] or Reads.pure(DEFAULT_SESSION_NUMBER)) and
    ((__ \ "CheckNumber").read[Int] or Reads.pure(DEFAULT_CHECK_NUMBER)) and
    (__ \ "Info").read[InfoData] and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(GetDataKKTResponse.apply _)

}
