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

import ca.aaronlevin.gitrev.gitHashShort
import nl.grons.metrics.scala.Timer
import com.github.alexanderfefelov.kkmserver.api.metrics.Instrumented
import com.github.alexanderfefelov.kkmserver.api.protocol._
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws.ning.NingWSClient
import play.api.libs.ws.{WSAuthScheme, WSResponse}

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class KkmServerApi extends Instrumented {

  val wsClient: NingWSClient = NingWSClient()

  def getServerData(request: GetServerDataRequest): Future[GetServerDataResponse] = {
    timeFuture[GetServerDataResponse](s"${request.Command}.0") {
      apiCall[GetServerDataRequest, GetServerDataResponse](request)
    }
  }

  def list(request: ListRequest): Future[ListResponse] = {
    timeFuture[ListResponse](s"${request.Command}.${request.NumDevice}") {
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

  def registerCheck(request: RegisterCheckRequest): Future[RegisterCheckResponse] = {
    timeFuture[RegisterCheckResponse](s"${request.Command}.${request.NumDevice}") {
      apiCall[RegisterCheckRequest, RegisterCheckResponse](request)
    }
  }

  def registerCheck10(request: RegisterCheckRequest10): Future[RegisterCheckResponse] = {
    timeFuture[RegisterCheckResponse](s"${request.Command}10.${request.NumDevice}") {
      apiCall[RegisterCheckRequest10, RegisterCheckResponse](request)
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
      logger.debug(s"response: ${response.json}")
      response.json.validate[B] match {
        case s: JsSuccess[B] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          response.json.validate[MinimalResponse] match {
            case s2: JsSuccess[MinimalResponse] =>
              throw KkmServerApiException(s2.value.Error)
            case _: JsError =>
              throw JsResultException(e.errors)
          }
      }
    }
  }

  private def wsCall(json: JsValue): Future[WSResponse] = {
    wsClient.url(KkmServerApiConfig.kkmServerUrl)
      .withAuth(KkmServerApiConfig.kkmServerUsername, KkmServerApiConfig.kkmServerPassword, WSAuthScheme.BASIC)
      .withHeaders("Accept" -> "application/json")
      .post(json)
  }

  private def timeFuture[T](prefix: String)(block: (Future[T])): Future[T] = {
    val metric = s"$prefix.time"
    val timer = timers.getOrElseUpdate(metric, metrics.timer(metric))
    timer.timeFuture(block)
  }

  private val logger = Logger("kkmserver-api")

  private val timers: mutable.Map[String, Timer] = scala.collection.mutable.Map[String, Timer]()

}

object KkmServerApi {

  val theGitHash: String = gitHashShort

}
