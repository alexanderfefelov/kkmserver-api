package com.github.alexanderfefelov.kkmserver.api.protocol

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class XReportRequest (
  // Meta
  //
  Command: String = COMMAND_X_REPORT,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Int
)

object XReportRequest {

  implicit val xReportRequestWrites: Writes[XReportRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").write[Int]
    )(unlift(XReportRequest.unapply))

}
