package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class RezultData (
  SessionNumber: Int,
  CheckNumber: Int,
  URL: String,
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String,
  NumDevice: Int,
  UnitName: String
)

case class GetRezultResponse (
  // Payload
  //
  Rezult: RezultData,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String
)

object GetRezultResponse {

  implicit val rezultDataReads: Reads[RezultData] = (
    ((__ \ "SessionNumber").read[Int] or Reads.pure(DEFAULT_SESSION_NUMBER)) and
    ((__ \ "CheckNumber").read[Int] or Reads.pure(DEFAULT_CHECK_NUMBER)) and
    ((__ \ "URL").read[String] or Reads.pure(DEFAULT_URL))and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String] and
    (__ \ "NumDevice").read[Int] and
    (__ \ "UnitName").read[String]
    )(RezultData.apply _)

  implicit val getRezultResponseReads: Reads[GetRezultResponse] = (
    (__ \ "Rezult").read[RezultData] and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String]
    )(GetRezultResponse.apply _)

}
