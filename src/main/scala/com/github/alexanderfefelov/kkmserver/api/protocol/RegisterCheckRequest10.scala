/*
 * Copyright (c) 2017-2018 Alexander Fefelov <alexanderfefelov@yandex.ru>
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

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class AdditionalProp10 (
  Print: Boolean,
  PrintInHeader: Boolean,
  NameProp: String,
  Prop: String
)

abstract class CheckStringData10
abstract class CheckString10

case class PrintImageCheckStringData10 (
  Image: String
) extends CheckStringData10

case class PrintImageCheckString10 (
  PrintImage: PrintImageCheckStringData10
) extends CheckString10

case class PrintTextCheckStringData10 (
  Text: String,
  Font: Option[Int] = None,
  Intensity: Option[Int] = None
) extends CheckStringData10

case class PrintTextCheckString10 (
  PrintText: PrintTextCheckStringData10
) extends CheckString10

case class RegisterCheckStringData10 (
  Name: String,
  Quantity: Double,
  Price: Double,
  Amount: Double,
  Department: Int = 0,
  Tax: Int = VAT_NO,
  EAN13: Option[String] = None
) extends CheckStringData10

case class RegisterCheckString10 (
  Register: RegisterCheckStringData10
) extends CheckString10

case class BarCodeCheckStringData10 (
  BarcodeType: String,
  Barcode: String
) extends CheckStringData10

case class BarCodeCheckString10 (
  BarCode: BarCodeCheckStringData10
) extends CheckString10

case class RegisterCheckRequest10 (
  // Meta
  //
  Command: String = COMMAND_REGISTER_CHECK,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Option[Int] = None,
  InnKkm: Option[String] = None,
  KktNumber: Option[String] = None,
  // Payload
  //
  IsFiscalCheck: Boolean,
  TypeCheck: Int,
  CancelOpenedCheck: Boolean,
  NotPrint: Boolean = false,
  CashierName: String,
  ClientAddress: Option[String] = None,
  TaxVariant: Option[String] = None,
  AdditionalProps: List[AdditionalProp10] = List(),
  KPP: Option[String] = None,
  CheckStrings: List[CheckString10],
  Cash: Double = 0.0,
  CashLessType1: Double = 0.0,
  CashLessType2: Double = 0.0,
  CashLessType3: Double = 0.0
)

object RegisterCheckRequest10 {

  implicit val additionalPropWrites10: Writes[AdditionalProp10] = (
    (__ \ "Print").write[Boolean] and
    (__ \ "PrintInHeader").write[Boolean] and
    (__ \ "NameProp").write[String] and
    (__ \ "Prop").write[String]
    )(unlift(AdditionalProp10.unapply))

  implicit val printTextCheckStringDataWrites10: Writes[PrintTextCheckStringData10] = (
    (__ \ "Text").write[String] and
    (__ \ "Font").writeNullable[Int] and
    (__ \ "Intensity").writeNullable[Int]
    )(unlift(PrintTextCheckStringData10.unapply))

  implicit val registerCheckStringDataWrites10: Writes[RegisterCheckStringData10] = (
    (__ \ "Name").write[String] and
    (__ \ "Quantity").write[Double] and
    (__ \ "Price").write[Double] and
    (__ \ "Amount").write[Double] and
    (__ \ "Department").write[Int] and
    (__ \ "Tax").write[Int] and
    (__ \ "EAN13").writeNullable[String]
    )(unlift(RegisterCheckStringData10.unapply))

  implicit val barCodeCheckStringDataWrites10: Writes[BarCodeCheckStringData10] = (
    (__ \ "BarcodeType").write[String] and
    (__ \ "Barcode").write[String]
    )(unlift(BarCodeCheckStringData10.unapply))

  implicit object checkStringDataWrites10 extends Writes[CheckStringData10] {
    override def writes(obj: CheckStringData10): JsValue = obj match {
      case pi: PrintImageCheckStringData10 => Json.obj("Image" -> pi.Image)
      case pt: PrintTextCheckStringData10 => Json.toJson(pt)(printTextCheckStringDataWrites10)
      case r: RegisterCheckStringData10 => Json.toJson(r)(registerCheckStringDataWrites10)
      case bc: BarCodeCheckStringData10 => Json.toJson(bc)(barCodeCheckStringDataWrites10)
      case _ => JsNull
    }
  }

  implicit object checkStringWrites10 extends Writes[CheckString10] {
    override def writes(obj: CheckString10): JsValue = obj match {
      case pi: PrintImageCheckString10 => Json.obj("PrintImage" -> pi.PrintImage)
      case pt: PrintTextCheckString10 => Json.obj("PrintText" -> pt.PrintText)
      case r: RegisterCheckString10 => Json.obj("Register" -> r.Register)
      case bc: BarCodeCheckString10 => Json.obj("BarCode" -> bc.BarCode)
      case _ => JsNull
    }
  }

  implicit val registerCheckRequest10Writes: Writes[RegisterCheckRequest10] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "InnKkm").writeNullable[String] and
    (__ \ "KktNumber").writeNullable[String] and
    (__ \ "IsFiscalCheck").write[Boolean] and
    (__ \ "TypeCheck").write[Int] and
    (__ \ "CancelOpenedCheck").write[Boolean] and
    (__ \ "NotPrint").write[Boolean] and
    (__ \ "CashierName").write[String] and
    (__ \ "ClientAddress").writeNullable[String] and
    (__ \ "TaxVariant").writeNullable[String] and
    (__ \ "AdditionalProps").write[List[AdditionalProp10]] and
    (__ \ "KPP").writeNullable[String] and
    (__ \ "CheckStrings").write[List[CheckString10]] and
    (__ \ "Cash").write[Double] and
    (__ \ "CashLessType1").write[Double] and
    (__ \ "CashLessType2").write[Double] and
    (__ \ "CashLessType3").write[Double]
    )(unlift(RegisterCheckRequest10.unapply))

}
