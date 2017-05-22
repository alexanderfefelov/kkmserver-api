package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CheckProp (
  Print: Boolean,
  PrintInHeader: Boolean,
  Teg: Int,
  Prop: String
)

case class AdditionalProp (
  Print: Boolean,
  PrintInHeader: Boolean,
  NameProp: String,
  Prop: String
)

abstract class CheckStringData {}
abstract class CheckString {}

case class PrintImageCheckStringData (
  Image: String
) extends CheckStringData

case class PrintIamgeCheckString (
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
  Tax: Int = -1,
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
  Command: String = CommandRegisterCheck,
  NumDevice: Option[Int] = None,
  InnKkm: Option[String] = None,
  KktNumber: Option[String] = None,
  IsFiscalCheck: Boolean,
  TypeCheck: Int,
  CancelOpenedCheck: Boolean,
  NotPrint: Boolean,
  CashierName: String,
  ClientAddress: Option[String] = None,
  TaxVariant: Option[String] = None,
  CheckProps: List[CheckProp],
  AdditionalProps: List[AdditionalProp],
  KPP: Option[String] = None,
  CheckStrings: List[CheckString],
  Cash: Double = 0.0,
  CashLessType1: Double = 0.0,
  CashLessType2: Double = 0.0,
  CashLessType3: Double = 0.0,
  IdCommand: String = createUuid
)

object RegisterCheckRequest {

  implicit val checkPropWrites: Writes[CheckProp] = (
    (JsPath \ "Print").write[Boolean] and
      (JsPath \ "PrintInHeader").write[Boolean] and
      (JsPath \ "Teg").write[Int] and
      (JsPath \ "Prop").write[String]
    )(unlift(CheckProp.unapply))

  implicit val additionalPropWrites: Writes[AdditionalProp] = (
    (JsPath \ "Print").write[Boolean] and
      (JsPath \ "PrintInHeader").write[Boolean] and
      (JsPath \ "NameProp").write[String] and
      (JsPath \ "Prop").write[String]
    )(unlift(AdditionalProp.unapply))

  implicit val printTextCheckStringDataWrites: Writes[PrintTextCheckStringData] = (
    (JsPath \ "Text").write[String] and
      (JsPath \ "Font").writeNullable[Int] and
      (JsPath \ "Intensity").writeNullable[Int]
    )(unlift(PrintTextCheckStringData.unapply))

  implicit val registerCheckStringDataWrites: Writes[RegisterCheckStringData] = (
    (JsPath \ "Name").write[String] and
      (JsPath \ "Quantity").write[Double] and
      (JsPath \ "Price").write[Double] and
      (JsPath \ "Amount").write[Double] and
      (JsPath \ "Department").write[Int] and
      (JsPath \ "Tax").write[Int] and
      (JsPath \ "EAN13").writeNullable[String]
    )(unlift(RegisterCheckStringData.unapply))

  implicit val barCodeCheckStringDataWrites: Writes[BarCodeCheckStringData] = (
    (JsPath \ "BarcodeType").write[String] and
      (JsPath \ "Barcode").write[String]
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
      case pi: PrintIamgeCheckString => Json.obj("PrintImage" -> pi.PrintImage)
      case pt: PrintTextCheckString => Json.obj("PrintText" -> pt.PrintText)
      case r: RegisterCheckString => Json.obj("Register" -> r.Register)
      case bc: BarCodeCheckString => Json.obj("BarCode" -> bc.BarCode)
      case _ => JsNull
    }
  }

  implicit val registerCheckRequestWrites: Writes[RegisterCheckRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "NumDevice").writeNullable[Int] and
      (JsPath \ "InnKkm").writeNullable[String] and
      (JsPath \ "KktNumber").writeNullable[String] and
      (JsPath \ "IsFiscalCheck").write[Boolean] and
      (JsPath \ "TypeCheck").write[Int] and
      (JsPath \ "CancelOpenedCheck").write[Boolean] and
      (JsPath \ "NotPrint").write[Boolean] and
      (JsPath \ "CashierName").write[String] and
      (JsPath \ "ClientAddress").writeNullable[String] and
      (JsPath \ "TaxVariant").writeNullable[String] and
      (JsPath \ "CheckProps").write[List[CheckProp]] and
      (JsPath \ "AdditionalProps").write[List[AdditionalProp]] and
      (JsPath \ "KPP").writeNullable[String] and
      (JsPath \ "CheckStrings").write[List[CheckString]] and
      (JsPath \ "Cash").write[Double] and
      (JsPath \ "CashLessType1").write[Double] and
      (JsPath \ "CashLessType2").write[Double] and
      (JsPath \ "CashLessType3").write[Double] and
      (JsPath \ "IdCommand").write[String]
    )(unlift(RegisterCheckRequest.unapply))

}

case class RegisterCheckResponse (
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  SessionNumber: Int,
  CheckNumber: Int,
  URL: String
)

object RegisterCheckResponse {

  implicit val registerCheckResponseReads: Reads[RegisterCheckResponse] = (
    (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String] and
      (JsPath \ "SessionNumber").read[Int] and
      (JsPath \ "CheckNumber").read[Int] and
      (JsPath \ "URL").read[String]
    )(RegisterCheckResponse.apply _)

}
