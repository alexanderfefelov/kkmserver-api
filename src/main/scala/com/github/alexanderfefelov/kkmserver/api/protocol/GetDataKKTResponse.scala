/*
 * Copyright (c) 2017 Alexander Fefelov <alexanderfefelov@yandex.ru>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 */

package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OrganizationData (
  NameOrganization: String,
  InnOrganization: String,
  TaxVariant: String,
  SenderEmail: String
)

case class OFDData (
  UrlServerOfd: String,
  PortServerOfd: String,
  NameOFD: String,
  UrlOFD: String,
  InnOFD: String,
  OFD_Error: String,
  OFD_NumErrorDoc: Int,
  OFD_DateErrorDoc: DateTime
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

case class FirmwareData (
  Firmware_Version: String,
  Firmware_Status: Int
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
  OFD: OFDData,
  FFD: FFDData,
  FN: FNData,
  Firmware: FirmwareData,
  Numbers: NumbersData,
  Settle: SettleData,
  DateTimeKKT: DateTime,
  SessionState: Int,
  BalanceCash: Double,
  OnOff: Boolean,
  Active: Boolean,
  PaperOver: Boolean
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

  implicit val ofdDataReads: Reads[OFDData] = (
    (__ \ "UrlServerOfd").read[String] and
    (__ \ "PortServerOfd").read[String] and
    (__ \ "NameOFD").read[String] and
    (__ \ "UrlOfd").read[String] and
    (__ \ "InnOfd").read[String] and
    (__ \ "OFD_Error").read[String] and
    (__ \ "OFD_NumErrorDoc").read[Int] and
    (__ \ "OFD_DateErrorDoc").read[DateTime](jodaReads)
    )(OFDData.apply _)

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

  implicit val firmwareDataReads: Reads[FirmwareData] = (
    (__ \ "Firmware_Version").read[String] and
    (__ \ "Firmware_Status").read[Int]
    )(FirmwareData.apply _)

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
    __.read[OrganizationData] and
    __.read[OFDData] and
    __.read[FFDData] and
    __.read[FNData] and
    __.read[FirmwareData] and
    __.read[NumbersData] and
    __.read[SettleData] and
    (__ \ "DateTimeKKT").read[DateTime](jodaReads) and
    (__ \ "SessionState").read[Int] and
    (__ \ "BalanceCash").read[Double] and
    (__ \ "OnOff").read[Boolean] and
    (__ \ "Active").read[Boolean] and
    (__ \ "PaperOver").read[Boolean]
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
    ((__ \ "UnitName").read[String] or Reads.pure(DEFAULT_UNITNAME))
    )(GetDataKKTResponse.apply _)

}
