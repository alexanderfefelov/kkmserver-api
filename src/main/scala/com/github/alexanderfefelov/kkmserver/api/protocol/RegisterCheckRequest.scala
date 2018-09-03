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

case class AdditionalProp (
  Print: Boolean,
  PrintInHeader: Boolean,
  NameProp: String,
  Prop: String
)

abstract class CheckStringData
abstract class CheckString

case class PrintImageCheckStringData (
  Image: String
) extends CheckStringData

case class PrintImageCheckString (
  PrintImage: PrintImageCheckStringData
) extends CheckString

case class PrintTextCheckStringData (
  Text: String,
  Font: Option[Int] = None,
  Intensity: Option[Int] = None
) extends CheckStringData

case class PrintTextCheckString (
  PrintText: PrintTextCheckStringData
) extends CheckString

case class RegisterCheckStringData (
  Name: String,
  Quantity: Double,
  Price: Double,
  Amount: Double,
  Department: Int = 0,
  Tax: Int = VAT_NO,
  SignMethodCalculation: Int = PAYMENT_METHOD_FULL,
  SignCalculationObject: Int = PAYMENT_SUBJECT_GOODS,
  EAN13: Option[String] = None
) extends CheckStringData

case class RegisterCheckString (
  Register: RegisterCheckStringData
) extends CheckString

case class BarCodeCheckStringData (
  BarcodeType: String,
  Barcode: String
) extends CheckStringData

case class BarCodeCheckString (
  BarCode: BarCodeCheckStringData
) extends CheckString

case class RegisterCheckRequest (
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
  CashierVATIN: String,
  SenderEmail: Option[String] = None,
  ClientAddress: Option[String] = None,
  TaxVariant: Option[String] = None,
  AdditionalProps: List[AdditionalProp] = List(),
  KPP: Option[String] = None,
  CheckStrings: List[CheckString],
  Cash: Double = 0.0,
  ElectronicPayment: Double = 0.0,
  AdvancePayment: Double = 0.0,
  Credit: Double = 0.0,
  CashProvision: Double = 0.0
)

object RegisterCheckRequest {

  implicit val additionalPropWrites: Writes[AdditionalProp] = (
    (__ \ "Print").write[Boolean] and
    (__ \ "PrintInHeader").write[Boolean] and
    (__ \ "NameProp").write[String] and
    (__ \ "Prop").write[String]
    )(unlift(AdditionalProp.unapply))

  implicit val printTextCheckStringDataWrites: Writes[PrintTextCheckStringData] = (
    (__ \ "Text").write[String] and
    (__ \ "Font").writeNullable[Int] and
    (__ \ "Intensity").writeNullable[Int]
    )(unlift(PrintTextCheckStringData.unapply))

  implicit val registerCheckStringDataWrites: Writes[RegisterCheckStringData] = (
    (__ \ "Name").write[String] and
    (__ \ "Quantity").write[Double] and
    (__ \ "Price").write[Double] and
    (__ \ "Amount").write[Double] and
    (__ \ "Department").write[Int] and
    (__ \ "Tax").write[Int] and
    (__ \ "SignMethodCalculation").write[Int] and
    (__ \ "SignCalculationObject").write[Int] and
    (__ \ "EAN13").writeNullable[String]
    )(unlift(RegisterCheckStringData.unapply))

  implicit val barCodeCheckStringDataWrites: Writes[BarCodeCheckStringData] = (
    (__ \ "BarcodeType").write[String] and
    (__ \ "Barcode").write[String]
    )(unlift(BarCodeCheckStringData.unapply))

  implicit object checkStringDataWrites extends Writes[CheckStringData] {
    override def writes(obj: CheckStringData): JsValue = obj match {
      case pi: PrintImageCheckStringData => Json.obj("Image" -> pi.Image)
      case pt: PrintTextCheckStringData => Json.toJson(pt)(printTextCheckStringDataWrites)
      case r: RegisterCheckStringData => Json.toJson(r)(registerCheckStringDataWrites)
      case bc: BarCodeCheckStringData => Json.toJson(bc)(barCodeCheckStringDataWrites)
      case _ => JsNull
    }
  }

  implicit object checkStringWrites extends Writes[CheckString] {
    override def writes(obj: CheckString): JsValue = obj match {
      case pi: PrintImageCheckString => Json.obj("PrintImage" -> pi.PrintImage)
      case pt: PrintTextCheckString => Json.obj("PrintText" -> pt.PrintText)
      case r: RegisterCheckString => Json.obj("Register" -> r.Register)
      case bc: BarCodeCheckString => Json.obj("BarCode" -> bc.BarCode)
      case _ => JsNull
    }
  }

  implicit val registerCheckRequestWrites: Writes[RegisterCheckRequest] = (
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
    (__ \ "CashierVATIN").write[String] and
    (__ \ "SenderEmail").writeNullable[String] and
    (__ \ "ClientAddress").writeNullable[String] and
    (__ \ "TaxVariant").writeNullable[String] and
    (__ \ "AdditionalProps").write[List[AdditionalProp]] and
    (__ \ "KPP").writeNullable[String] and
    (__ \ "CheckStrings").write[List[CheckString]] and
    (__ \ "Cash").write[Double] and
    (__ \ "ElectronicPayment").write[Double] and
    (__ \ "AdvancePayment").write[Double] and
    (__ \ "Credit").write[Double] and
    (__ \ "CashProvision").write[Double]
    )(unlift(RegisterCheckRequest.unapply))

}
