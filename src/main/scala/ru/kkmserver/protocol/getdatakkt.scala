package ru.kkmserver.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class GetDataKKTRequest (
  // Meta
  //
  Command: String = CommandGetDataKKT,
  NumDevice: Option[Int] = None,
  IdCommand: String = createUuid
)

object GetDataKKTRequest {

  implicit val getDataKKTRequestWrites: Writes[GetDataKKTRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "IdCommand").write[String]
    )(unlift(GetDataKKTRequest.unapply))

}

case class InfoData (
  SessionState: Int,
  PaperOver: Boolean,
  LicenseExpirationDate: DateTime
)

case class GetDataKKTResponse (
  // Payload
  //
  SessionNumber: Int,
  Info: InfoData,
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object GetDataKKTResponse {

  implicit val infoDataReads: Reads[InfoData] = (
    (__ \ "SessionState").read[Int] and
    (__ \ "PaperOver").read[Boolean] and
    (__ \ "LicenseExpirationDate").read[DateTime](jodaReadsShort)
    )(InfoData.apply _)

  implicit val getDataKKTResponseReads: Reads[GetDataKKTResponse] = (
    (__ \ "SessionNumber").read[Int] and
    (__ \ "Info").read[InfoData] and
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(GetDataKKTResponse.apply _)

}
