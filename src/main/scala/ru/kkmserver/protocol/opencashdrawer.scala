package ru.kkmserver.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OpenCashDrawerRequest (
  // Meta
  //
  Command: String = CommandOpenCashDrawer,
  NumDevice: Option[Int] = None,
  IdCommand: String = createUuid
)

object OpenCashDrawerRequest {

  implicit val openCashDrawerRequestWrites: Writes[OpenCashDrawerRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "IdCommand").write[String]
    )(unlift(OpenCashDrawerRequest.unapply))

}

case class OpenCashDrawerResponse (
  // Meta
  //
  Command: String,
  Error: String,
  Status: Int,
  IdCommand: String,
  NumDevice: Int
)

object OpenCashDrawerResponse {

  implicit val openCashDrawerResponseReads: Reads[OpenCashDrawerResponse] = (
    (__ \ "Command").read[String] and
    (__ \ "Error").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "IdCommand").read[String] and
    (__ \ "NumDevice").read[Int]
    )(OpenCashDrawerResponse.apply _)

}
