package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ServerData (
  ServerName: String,
  ServerVersion: String,
  ServerDateStart: DateTime,
  ServerUpTime: String,
  LicenseExpirationDate: DateTime,
  LicenseCount: Int,
  OSVersion: String,
  OSPlatform: String,
  OSVersionString: String,
  OSName: String,
  OSDateStart: DateTime,
  OSUpTime: String,
  OSCurDateTime: DateTime,
  PCServerName: String,
  PCUserName: String,
  PCPhysicalMemory: String,
  PCFreePhysicalMemory: String,
  // PCFreeDiskSpace: String,
  PCProcessorName: String,
  PCNumberOfCores: String
)

case class GetServerDataResponse (
  // Payload
  //
  Data: ServerData,
  // Meta
  //
  Command: String,
  IdCommand: String,
  Status: Int,
  Error: String
)

object GetServerDataResponse {

  implicit val serverDataReads: Reads[ServerData] = (
    (__ \ "ServerName").read[String] and
    (__ \ "ServerVersion").read[String] and
    (__ \ "ServerDateStart").read[DateTime](jodaReads) and
    (__ \ "ServerUpTime").read[String] and
    (__ \ "LicenseExpirationDate").read[DateTime](jodaReads) and
    (__ \ "LicenseCount").read[Int] and
    (__ \ "OSVersion").read[String] and
    (__ \ "OSPlatform").read[String] and
    (__ \ "OSVersionString").read[String] and
    (__ \ "OSName").read[String] and
    (__ \ "OSDateStart").read[DateTime](jodaReads) and
    (__ \ "OSUpTime").read[String] and
    (__ \ "OSCurDateTime").read[DateTime](jodaReads) and
    (__ \ "PCServerName").read[String] and
    (__ \ "PCUserName").read[String] and
    (__ \ "PCPhysicalMemory").read[String] and
    (__ \ "PCFreePhysicalMemory").read[String] and
    // (__ \ "PCFreeDiskSpace").read[String] and
    (__ \ "PCProcessorName").read[String] and
    (__ \ "PCNumberOfCores").read[String]
    )(ServerData.apply _)

  implicit val getServerDataResponseReads: Reads[GetServerDataResponse] = (
    (__ \ "ServerData").read[ServerData] and
    (__ \ "Command").read[String] and
    (__ \ "IdCommand").read[String] and
    (__ \ "Status").read[Int] and
    (__ \ "Error").read[String]
    )(GetServerDataResponse.apply _)

}
