/*
 * Copyright (c) 2017-2018 Alexander Fefelov <alexanderfefelov@yandex.ru>
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

import java.nio.charset.Charset

import ca.aaronlevin.gitrev.gitHashShort
import com.github.alexanderfefelov.kkmserver.api.metrics.Instrumented
import com.github.alexanderfefelov.kkmserver.api.protocol._
import com.typesafe.scalalogging._
import nl.grons.metrics.scala.Timer
import play.api.libs.json._
import requests._

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class KkmServerApi extends Instrumented {

  def getServerData(request: GetServerDataRequest): Future[GetServerDataResponse] = {
    timeFuture[GetServerDataResponse](s"${request.Command}.0") {
      apiCall[GetServerDataRequest, GetServerDataResponse](request)
    }
  }

  def list(request: ListRequest): Future[ListResponse] = {
    timeFuture[ListResponse](s"${request.Command}.0") {
      apiCall[ListRequest, ListResponse](request)
    }
  }

  def xReport(request: XReportRequest): Future[XReportResponse] = {
    timeFuture[XReportResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[XReportRequest, XReportResponse](request)
    }
  }

  def zReport(request: ZReportRequest): Future[ZReportResponse] = {
    timeFuture[ZReportResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[ZReportRequest, ZReportResponse](request)
    }
  }

  def registerCheck(request: RegisterCheckRequest, printId: Boolean = false): Future[RegisterCheckResponse] = {
    val modifiedRequest = if (printId)
      request.copy(AdditionalProps = AdditionalProp(Print = true, PrintInHeader = true, NameProp = "ID", Prop = request.IdCommand) :: request.AdditionalProps)
    else request
    timeFuture[RegisterCheckResponse](s"${modifiedRequest.Command}.${modifiedRequest.NumDevice.getOrElse(0)}") {
      apiCall[RegisterCheckRequest, RegisterCheckResponse](modifiedRequest)
    }
  }

  def registerCheck10(request: RegisterCheckRequest10, printId: Boolean = false): Future[RegisterCheckResponse] = {
    val modifiedRequest = if (printId)
      request.copy(AdditionalProps = AdditionalProp10(Print = true, PrintInHeader = true, NameProp = "ID", Prop = request.IdCommand) :: request.AdditionalProps)
    else
      request
    timeFuture[RegisterCheckResponse](s"${modifiedRequest.Command}10.${modifiedRequest.NumDevice.getOrElse(0)}") {
      apiCall[RegisterCheckRequest10, RegisterCheckResponse](modifiedRequest)
    }
  }

  def getRezult(request: GetRezultRequest): Future[GetRezultResponse] = {
    timeFuture[GetRezultResponse](s"${request.Command}.0") {
      apiCall[GetRezultRequest, GetRezultResponse](request)
    }
  }

  def getDataCheck(request: GetDataCheckRequest): Future[GetDataCheckResponse] = {
    timeFuture[GetDataCheckResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[GetDataCheckRequest, GetDataCheckResponse](request)
    }
  }

  def getDataKKT(request: GetDataKKTRequest): Future[GetDataKKTResponse] = {
    timeFuture[GetDataKKTResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[GetDataKKTRequest, GetDataKKTResponse](request)
    }
  }

  def ofdReport(request: OfdReportRequest): Future[OfdReportResponse] = {
    timeFuture[OfdReportResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[OfdReportRequest, OfdReportResponse](request)
    }
  }

  def paymentCash(request: PaymentCashRequest): Future[PaymentCashResponse] = {
    timeFuture[PaymentCashResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[PaymentCashRequest, PaymentCashResponse](request)
    }
  }

  def depositingCash(request: DepositingCashRequest): Future[DepositingCashResponse] = {
    timeFuture[DepositingCashResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[DepositingCashRequest, DepositingCashResponse](request)
    }
  }

  def openShift(request: OpenShiftRequest): Future[OpenShiftResponse] = {
    timeFuture[OpenShiftResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[OpenShiftRequest, OpenShiftResponse](request)
    }
  }

  def closeShift(request: CloseShiftRequest): Future[CloseShiftResponse] = {
    timeFuture[CloseShiftResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[CloseShiftRequest, CloseShiftResponse](request)
    }
  }

  def onOffUnut(request: OnOffUnutRequest): Future[OnOffUnutResponse] = {
    timeFuture[OnOffUnutResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[OnOffUnutRequest, OnOffUnutResponse](request)
    }
  }

  def openCashDrawer(request: OpenCashDrawerRequest): Future[OpenCashDrawerResponse] = {
    timeFuture[OpenCashDrawerResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[OpenCashDrawerRequest, OpenCashDrawerResponse](request)
    }
  }

  private def apiCall[A : Writes, B : Reads](request: A): Future[B] = {
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = wsCall(requestJson)
    for {
      response <- responseFuture
    } yield {
      val responseText = response.text(Charset.forName("UTF-8"))
      logger.debug(s"response: $responseText")
      try {
        val json = Json.parse(responseText)
        json.validate[B] match {
          case s: JsSuccess[B] =>
            s.value
          case e: JsError =>
            logger.error(s"error: ${e.errors}")
            json.validate[MinimalResponse] match {
              case s2: JsSuccess[MinimalResponse] =>
                throw KkmServerApiException(s2.value.Error)
              case _: JsError =>
                throw JsResultException(e.errors)
            }
        }
      } catch {
        case e: com.fasterxml.jackson.databind.exc.MismatchedInputException =>
          logger.error(s"error: ${e.getMessage}")
          throw KkmServerApiException(e.getMessage)
      }
    }
  }

  private def wsCall(json: JsValue): Future[Response] = {
    Future(requests.post(
      url = KkmServerApiConfig.kkmServerUrl,
      auth = RequestAuth.implicitBasic((KkmServerApiConfig.kkmServerUsername, KkmServerApiConfig.kkmServerPassword)),
      data = json.toString().getBytes(Charset.forName("UTF-8")),
      connectTimeout = KkmServerApiConfig.kkmServerConnectTimeout,
      readTimeout = KkmServerApiConfig.kkmServerReadTimeout
    ))
  }

  private def timeFuture[T](metricName: String)(block: Future[T]): Future[T] = {
    val timer = timers.getOrElseUpdate(metricName, metrics.timer(metricName))
    timer.timeFuture(block)
  }

  private val logger = Logger("kkmserver-api")

  private val timers: mutable.Map[String, Timer] = scala.collection.mutable.Map[String, Timer]()

  logger.info(s"KkmServer API URL: ${KkmServerApiConfig.kkmServerUrl} Username: ${KkmServerApiConfig.kkmServerUsername}")
  if (KkmServerApiConfig.graphiteEnabled) {
    logger.info(s"KkmServer API Graphite: ${KkmServerApiConfig.graphiteHost}:${KkmServerApiConfig.graphitePort} Prefix: ${KkmServerApiConfig.graphitePrefix}")
  }

}

object KkmServerApi {

  val theGitHash: String = gitHashShort

}
