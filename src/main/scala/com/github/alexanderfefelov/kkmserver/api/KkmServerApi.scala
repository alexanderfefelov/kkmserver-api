package com.github.alexanderfefelov.kkmserver.api

import ca.aaronlevin.gitrev.gitHashShort
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws.{WSAuthScheme, WSResponse}
import play.api.libs.ws.ning.NingWSClient
import com.github.alexanderfefelov.kkmserver.api.protocol._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class KkmServerApi {

  val wsClient: NingWSClient = NingWSClient()

  def getServerData(request: GetServerDataRequest): Future[GetServerDataResponse] = {
    apiCall[GetServerDataRequest, GetServerDataResponse](request)
  }

  def list(request: ListRequest): Future[ListResponse] = {
    apiCall[ListRequest, ListResponse](request)
  }

  def xReport(request: XReportRequest): Future[XReportResponse] = {
    apiCall[XReportRequest, XReportResponse](request)
  }

  def zReport(request: ZReportRequest): Future[ZReportResponse] = {
    apiCall[ZReportRequest, ZReportResponse](request)
  }

  def registerCheck(request: RegisterCheckRequest): Future[RegisterCheckResponse] = {
    apiCall[RegisterCheckRequest, RegisterCheckResponse](request)
  }

  def registerCheck10(request: RegisterCheckRequest10): Future[RegisterCheckResponse10] = {
    apiCall[RegisterCheckRequest10, RegisterCheckResponse10](request)
  }

  def getRezult(request: GetRezultRequest): Future[GetRezultResponse] = {
    apiCall[GetRezultRequest, GetRezultResponse](request)
  }

  def getDataCheck(request: GetDataCheckRequest): Future[GetDataCheckResponse] = {
    apiCall[GetDataCheckRequest, GetDataCheckResponse](request)
  }

  def getDataKKT(request: GetDataKKTRequest): Future[GetDataKKTResponse] = {
    apiCall[GetDataKKTRequest, GetDataKKTResponse](request)
  }

  def ofdReport(request: OfdReportRequest): Future[OfdReportResponse] = {
    apiCall[OfdReportRequest, OfdReportResponse](request)
  }

  def paymentCash(request: PaymentCashRequest): Future[PaymentCashResponse] = {
    apiCall[PaymentCashRequest, PaymentCashResponse](request)
  }

  def depositingCash(request: DepositingCashRequest): Future[DepositingCashResponse] = {
    apiCall[DepositingCashRequest, DepositingCashResponse](request)
  }

  def openShift(request: OpenShiftRequest): Future[OpenShiftResponse] = {
    apiCall[OpenShiftRequest, OpenShiftResponse](request)
  }

  def closeShift(request: CloseShiftRequest): Future[CloseShiftResponse] = {
    apiCall[CloseShiftRequest, CloseShiftResponse](request)
  }

  def onOffUnut(request: OnOffUnutRequest): Future[OnOffUnutResponse] = {
    apiCall[OnOffUnutRequest, OnOffUnutResponse](request)
  }

  def openCashDrawer(request: OpenCashDrawerRequest): Future[OpenCashDrawerResponse] = {
    apiCall[OpenCashDrawerRequest, OpenCashDrawerResponse](request)
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
    wsClient.url(KkmServerApiConfig.url)
      .withAuth(KkmServerApiConfig.username, KkmServerApiConfig.password, WSAuthScheme.BASIC)
      .withHeaders("Accept" -> "application/json")
      .post(json)
  }

  private val logger = Logger("kkmserver-api")

}

object KkmServerApi {

  val theGitHash: String = gitHashShort

}
