package ru.kkmserver

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatterBuilder}
import play.api.libs.json.Reads

package object protocol {

  private val jodaFormatShort = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")
  private val jodaFormatLong = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ")
  private val jodaParsers = Array(jodaFormatShort.getParser, jodaFormatLong.getParser)
  private val jodaFormatter = new DateTimeFormatterBuilder().append(null, jodaParsers).toFormatter

  val jodaReads: Reads[DateTime] = Reads.of[String] map (x => jodaFormatter.parseDateTime(x))

  def createUuid: String = java.util.UUID.randomUUID().toString

  // Команды
  //
  val COMMAND_GET_REZULT: String = "GetRezult"            // Проверить статус команды
  val COMMAND_LIST: String = "List"                       // Получить список устройств
  val COMMAND_REGISTER_CHECK: String = "RegisterCheck"    // Напечатать чек
  val COMMAND_X_REPORT: String = "XReport"                // Напечатать X-отчет
  val COMMAND_Z_REPORT: String = "ZReport"                // Напечатать Z-отчет
  val COMMAND_GET_DATA_KKT: String = "GetDataKKT"         // Получить состояние ККТ
  val COMMAND_OFD_REPORT: String = "OfdReport"            // Проверить состояние связи и расчетов с ОФД
  val COMMAND_PAYMENT_CASH: String = "PaymentCash"        // Изъять наличные
  val COMMAND_DEPOSITING_CASH: String = "DepositingCash"  // Внести наличные
  val COMMAND_OPEN_SHIFT: String = "OpenShift"            // Открыть смену
  val COMMAND_OPEN_CASH_DRAWER: String = "OpenCashDrawer" // Открыть денежный ящик

  // Статусы команд
  //
  val COMMAND_STATUS_OK: Int = 0          // Ok
  val COMMAND_STATUS_RUN: Int = 1         // Выполняется
  val COMMAND_STATUS_ERROR: Int = 2       // Ошибка
  val COMMAND_STATUS_NOT_FOUND: Int = 3   // Не найдена
  val COMMAND_STATUS_NOT_RUN: Int = 4     // Ожидает выполнения
  val COMMAND_STATUS_DONE: Int = 5        // Выполнена ранее
  val COMMAND_STATUS_EGAIS_ERROR: Int = 6 // Ошибка ЕГАИС

  // Типы чеков
  //
  val CHECK_TYPE_SALE: Int = 0              // Продажа
  val CHECK_TYPE_SALE_RETURN: Int = 1       // Возврат продажи
  val CHECK_TYPE_EGAIS_SALE: Int = 8        // Продажа ЕГАИС
  val CHECK_TYPE_EGAIS_SALE_RETURN: Int = 9 // Возврат продажи ЕГАИС
  val CHECK_TYPE_PURCHASE: Int = 10         // Покупка
  val CHECK_TYPE_PURCHASE_RETURN: Int = 11  // Возврат покупки

  // Состояния сессий
  //
  val SESSION_STATE_CLOSED: Int = 1  // Открыта
  val SESSION_STATE_OPEN: Int = 2    // Закрыта
  val SESSION_STATE_EXPIRED: Int = 3 // Закончилась

}
