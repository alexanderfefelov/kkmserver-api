package ru.kkmserver.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class GetDataKKTRequest (
  Command: String = CommandGetDataKKT,
  NumDevice: Option[Int] = None,
  IdCommand: String = createUuid
)

object GetDataKKTRequest {

  implicit val getDataKKTRequestWrites: Writes[GetDataKKTRequest] = (
    (JsPath \ "Command").write[String] and
      (JsPath \ "NumDevice").writeNullable[Int] and
      (JsPath \ "IdCommand").write[String]
    )(unlift(GetDataKKTRequest.unapply))

}

case class InfoData (
  SessionState: Int,
  PaperOver: Boolean,
  LicenseExpirationDate: DateTime
)

case class GetDataKKTResponse (
  SessionNumber: Int,
  Info: InfoData,
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object GetDataKKTResponse {

  implicit val infoDataReads: Reads[InfoData] = (
    (JsPath \ "SessionState").read[Int] and
      (JsPath \ "PaperOver").read[Boolean] and
      (JsPath \ "LicenseExpirationDate").read[DateTime](jodaReadsShort)
    )(InfoData.apply _)

  implicit val getDataKKTResponseReads: Reads[GetDataKKTResponse] = (
    (JsPath \ "SessionNumber").read[Int] and
      (JsPath \ "Info").read[InfoData] and
      (JsPath \ "Command").read[String] and
      (JsPath \ "Error").read[String] and
      (JsPath \ "Status").read[Int] and
      (JsPath \ "IdCommand").read[String] and
      (JsPath \ "NumDevice").read[Int]
    )(GetDataKKTResponse.apply _)

}
