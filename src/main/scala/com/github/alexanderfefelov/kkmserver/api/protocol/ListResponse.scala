/*
 * Copyright (c) 2017-2019 Alexander Fefelov <alexanderfefelov@yandex.ru>
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

case class ListUnit (
  NumDevice: Int,
  IdDevice: String,
  OnOff: Boolean,
  Active: Boolean,
  TypeDevice: String,
  IdTypeDevice: String,
  Firmware_Version: String,
  IP: String,
  NameDevice: String,
  UnitName: String,
  Numbers: NumbersData,
  INN: String,
  NameOrganization: String,
  TaxVariant: String,
  AddDate: DateTime,
  OFDError: OFDErrorData,
  FN: FNData,
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
    (__ \ "Firmware_Version").read[String] and
    (__ \ "IP").read[String] and
    (__ \ "NameDevice").read[String] and
    (__ \ "UnitName").read[String] and
    __.read[NumbersData] and
    (__ \ "INN").read[String] and
    (__ \ "NameOrganization").read[String] and
    (__ \ "TaxVariant").read[String] and
    (__ \ "AddDate").read[DateTime](jodaReads) and
    __.read[OFDErrorData] and
    __.read[FNData] and
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
