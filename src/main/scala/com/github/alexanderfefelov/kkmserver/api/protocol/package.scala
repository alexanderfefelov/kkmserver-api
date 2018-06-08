/*
 * Copyright (c) 2017 Alexander Fefelov <alexanderfefelov@yandex.ru>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 */

package com.github.alexanderfefelov.kkmserver.api

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatterBuilder}
import play.api.libs.functional.syntax._
import play.api.libs.json._

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
  val COMMAND_REGISTER_CHECK: String = "RegisterCheck"    // Зарегистрировать чек
  val COMMAND_X_REPORT: String = "XReport"                // Напечатать X-отчет
  val COMMAND_Z_REPORT: String = "ZReport"                // Напечатать Z-отчет
  val COMMAND_GET_DATA_KKT: String = "GetDataKKT"         // Получить состояние ККТ
  val COMMAND_OFD_REPORT: String = "OfdReport"            // Проверить состояние связи и расчетов с ОФД
  val COMMAND_PAYMENT_CASH: String = "PaymentCash"        // Изъять наличные
  val COMMAND_DEPOSITING_CASH: String = "DepositingCash"  // Внести наличные
  val COMMAND_OPEN_SHIFT: String = "OpenShift"            // Открыть смену
  val COMMAND_CLOSE_SHIFT: String = "CloseShift"          // Закрыть смену
  val COMMAND_OPEN_CASH_DRAWER: String = "OpenCashDrawer" // Открыть денежный ящик
  val COMMAND_GET_SERVER_DATA: String = "GetServerData"   // Получить информацию о сервере
  val COMMAND_GET_DATA_CHECK: String = "GetDataCheck"     // Получить информацию о чеке и, возможно, напечатать его копию
  val COMMAND_ON_OFF_UNUT: String = "OnOffUnut"           // Включить/выключить устройство

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
  val CHECK_TYPE_SALE: Int = 0                  // Продажа
  val CHECK_TYPE_SALE_RETURN: Int = 1           // Возврат продажи
  val CHECK_TYPE_SALE_ADJUSTMENT: Int = 2       // Корректировка продажи
  val CHECK_TYPE_EGAIS_SALE: Int = 8            // Продажа ЕГАИС
  val CHECK_TYPE_EGAIS_SALE_RETURN: Int = 9     // Возврат продажи ЕГАИС
  val CHECK_TYPE_PURCHASE: Int = 10             // Покупка
  val CHECK_TYPE_PURCHASE_RETURN: Int = 11      // Возврат покупки
  val CHECK_TYPE_PURCHASE_ADJUSTMENT: Int = 12  // Корректировка покупки

  // Состояния сессий
  //
  val SESSION_STATE_CLOSED: Int = 1  // Закрыта
  val SESSION_STATE_OPEN: Int = 2    // Открыта
  val SESSION_STATE_EXPIRED: Int = 3 // Закончилась

  // Системы налогообложения (тег 1055)
  //
  val SYSTEM_OF_TAXATION_0: String = "0" // Общая ОСН
  val SYSTEM_OF_TAXATION_1: String = "1" // Упрощенная УСН (Доход)
  val SYSTEM_OF_TAXATION_2: String = "2" // Упрощенная УСН (Доход минус Расход)
  val SYSTEM_OF_TAXATION_3: String = "3" // Единый налог на вмененный доход ЕНВД
  val SYSTEM_OF_TAXATION_4: String = "4" // Единый сельскохозяйственный налог ЕСН
  val SYSTEM_OF_TAXATION_5: String = "5" // Патентная система налогообложения

  // Признаки способа расчета (тег 1214)
  //
  val PAYMENT_METHOD_PREPAYMENT = 1   // Полная предоплата
  val PAYMENT_METHOD_DOWN_PAYMENT = 2 // Частичная предоплата
  val PAYMENT_METHOD_ADVANCE = 3      // Аванс
  val PAYMENT_METHOD_FULL = 4         // Полная оплата

  // Признаки предмета расчета (тег 1212)
  //
  val PAYMENT_SUBJECT_GOODS = 1                 // Товар
  val PAYMENT_SUBJECT_EXCISE_GOODS = 2          // Акцизный товар
  val PAYMENT_SUBJECT_WORK = 3                  // Работа
  val PAYMENT_SUBJECT_SERVICE = 4               // Услуга
  val PAYMENT_SUBJECT_GAMBLING_BET = 5          // Ставка в азартной игре
  val PAYMENT_SUBJECT_GAMBLING_GAIN = 6         // Выигрыш в азартной игре
  val PAYMENT_SUBJECT_LOTTERY_BET = 7           // Ставка в лотерею
  val PAYMENT_SUBJECT_LOTTERY_GAIN = 8          // Выигрыш в лотерею
  val PAYMENT_SUBJECT_INTELLECTUAL_PROPERTY = 9 // Права на использование результатов интеллектуальной деятельности
  val PAYMENT_SUBJECT_PAYMENT = 10              // Платёж
  val PAYMENT_SUBJECT_AGENT_COMMISSION = 11     // Агентское вознаграждение
  val PAYMENT_SUBJECT_COMPOSITE = 12            // Составной
  val PAYMENT_SUBJECT_OTHER = 13                // Прочее

  // НДС
  //
  val VAT_NO: Int = -1 // Не облагается
  val VAT_0: Int = 0
  val VAT_10: Int = 10
  val VAT_18: Int = 18
  val VAT_110: Int = 110
  val VAT_118: Int = 118

  // Значения по умолчанию для ответов
  //
  val DEFAULT_URL: String = ""
  val DEFAULT_QRCODE: String = ""
  val DEFAULT_UNITNAME: String = ""
  val DEFAULT_SESSION_NUMBER: Int = -1
  val DEFAULT_CHECK_NUMBER: Int = -1

  case class FNData (
    FN_IsFiscal: Boolean,
    FN_MemOverflowl: Boolean,
    FN_DateEnd: DateTime
  )

  implicit val fnDataReads: Reads[FNData] = (
    (__ \ "FN_IsFiscal").read[Boolean] and
    (__ \ "FN_MemOverflowl").read[Boolean] and
    (__ \ "FN_DateEnd").read[DateTime](jodaReads)
    )(FNData.apply _)

  case class NumbersData (
    KktNumber: String,
    FnNumber: String,
    RegNumber: String
  )

  implicit val numbersDataReads: Reads[NumbersData] = (
    (__ \ "KktNumber").read[String] and
    (__ \ "FnNumber").read[String] and
    (__ \ "RegNumber").read[String]
    )(NumbersData.apply _)

  case class OFDErrorData (
    OFD_Error: String,
    OFD_NumErrorDoc: Int,
    OFD_DateErrorDoc: DateTime
  )

  implicit val ofdErrorDataReads: Reads[OFDErrorData] = (
    (__ \ "OFD_Error").read[String] and
    (__ \ "OFD_NumErrorDoc").read[Int] and
    (__ \ "OFD_DateErrorDoc").read[DateTime](jodaReads)
    )(OFDErrorData.apply _)

}
