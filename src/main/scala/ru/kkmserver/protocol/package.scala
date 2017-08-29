package ru.kkmserver

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Reads

package object protocol {

  private val jodaFormatShort = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")
  private val jodaFormatLong = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ")

  val jodaReadsShort: Reads[DateTime] = Reads.of[String] map (DateTime.parse(_, jodaFormatShort))
  val jodaReadsLong: Reads[DateTime] = Reads.of[String] map (DateTime.parse(_, jodaFormatLong))

  def createUuid: String = java.util.UUID.randomUUID().toString

  // Проверить статус команды
  val CommandGetRezult: String = "GetRezult"
  // Получить список устройств
  val CommandList: String = "List"
  // Напечатать чек
  val CommandRegisterCheck: String = "RegisterCheck"
  // Напечатать X-отчет
  val CommandXReport: String = "XReport"
  // Напечатать Z-отчет
  val CommandZReport: String = "ZReport"
  // Получить состояние ККТ
  val CommandGetDataKKT: String = "GetDataKKT"
  // Проверить состояние связи и расчетов с ОФД
  val CommandOfdReport: String = "OfdReport"
  // Изъять наличные
  val CommandPaymentCash: String = "PaymentCash"

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
