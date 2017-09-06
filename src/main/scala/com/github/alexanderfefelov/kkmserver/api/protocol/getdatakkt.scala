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

case class InfoData (
  SessionState: Int,
  BalanceCash: Double,
  PaperOver: Boolean,
  NameOrganization: String,
  InnOrganization: String,
  AddressSettle: String,
  KktNumber: String,
  FnNumber: String,
  RegNumber: String,
  FFDVersion: String,
  FFDVersionFN: String,
  FFDVersionKKT: String,
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

  implicit val infoDataReads: Reads[InfoData] = (
    (__ \ "SessionState").read[Int] and
    (__ \ "BalanceCash").read[Double] and
    (__ \ "PaperOver").read[Boolean] and
    (__ \ "NameOrganization").read[String] and
    (__ \ "InnOrganization").read[String] and
    (__ \ "AddressSettle").read[String] and
    (__ \ "KktNumber").read[String] and
    (__ \ "FnNumber").read[String] and
    (__ \ "RegNumber").read[String] and
    (__ \ "FFDVersion").read[String] and
    (__ \ "FFDVersionFN").read[String] and
    (__ \ "FFDVersionKKT").read[String] and
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
