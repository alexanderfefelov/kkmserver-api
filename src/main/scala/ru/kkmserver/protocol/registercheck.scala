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

abstract class CheckStringData
abstract class CheckString

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
  NotPrint: Boolean,
  CashierName: String,
  ClientAddress: Option[String] = None,
  TaxVariant: Option[String] = None,
  CheckProps: List[CheckProp] = List(),
  AdditionalProps: List[AdditionalProp] = List(),
  KPP: Option[String] = None,
  CheckStrings: List[CheckString],
  Cash: Double = 0.0,
  CashLessType1: Double = 0.0,
  CashLessType2: Double = 0.0,
  CashLessType3: Double = 0.0
)

object RegisterCheckRequest {

  implicit val checkPropWrites: Writes[CheckProp] = (
    (__ \ "Print").write[Boolean] and
    (__ \ "PrintInHeader").write[Boolean] and
    (__ \ "Teg").write[Int] and
    (__ \ "Prop").write[String]
    )(unlift(CheckProp.unapply))

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
      case pi: PrintIamgeCheckString => Json.obj("PrintImage" -> pi.PrintImage)
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
    (__ \ "ClientAddress").writeNullable[String] and
    (__ \ "TaxVariant").writeNullable[String] and
    (__ \ "CheckProps").write[List[CheckProp]] and
    (__ \ "AdditionalProps").write[List[AdditionalProp]] and
    (__ \ "KPP").writeNullable[String] and
    (__ \ "CheckStrings").write[List[CheckString]] and
    (__ \ "Cash").write[Double] and
    (__ \ "CashLessType1").write[Double] and
    (__ \ "CashLessType2").write[Double] and
    (__ \ "CashLessType3").write[Double]
    )(unlift(RegisterCheckRequest.unapply))

}

case class RegisterCheckResponse (
  // Payload
  //
  SessionNumber: Int,
  CheckNumber: Int,
  URL: String,
  QRCode: String,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

object RegisterCheckResponse {

  implicit val registerCheckResponseReads: Reads[RegisterCheckResponse] = (
    ((__ \ "SessionNumber").read[Int] or Reads.pure(DEFAULT_SESSION_NUMBER)) and
    ((__ \ "CheckNumber").read[Int] or Reads.pure(DEFAULT_CHECK_NUMBER)) and
    ((__ \ "URL").read[String] or Reads.pure(DEFAULT_URL)) and
    ((__ \ "QRCode").read[String] or Reads.pure(DEFAULT_QRCODE)) and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(RegisterCheckResponse.apply _)

}
