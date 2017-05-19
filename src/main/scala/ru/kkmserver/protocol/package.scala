package ru.kkmserver

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Reads

package object protocol {

  val jodaReadsShort: Reads[DateTime] = Reads.of[String] map (DateTime.parse(_, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")))
  val jodaReadsLong: Reads[DateTime] = Reads.of[String] map (DateTime.parse(_, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ")))

  def createUuid: String = java.util.UUID.randomUUID().toString

  val CommandGetRezult: String = "GetRezult"
  val CommandList: String = "List"
  val CommandRegisterCheck: String = "RegisterCheck"
  val CommandXReport: String = "XReport"
  val CommandZReport: String = "ZReport"
  val CommandGetDataKKT: String = "GetDataKKT"

  val CommandStatusOk: Int = 0
  val CommandStatusRun: Int = 1
  val CommandStatusError: Int = 2
  val CommandStatusNotFound: Int = 3
  val CommandStatusNotRun: Int = 4

  val TypeCheckSale: Int = 0
  val TypeCheckSaleReturn: Int = 1
  val TypeCheckEgaisSale: Int = 8
  val TypeCheckEgaisSaleReturn: Int = 9
  val TypeCheckPurchase: Int = 10
  val TypeCheckPurchaseReturn: Int = 11

  val SessionStateClosed: Int = 1
  val SessionStateOpen: Int = 2
  val SessionStateExpired: Int = 3

}
