package ru.kkmserver

import ca.aaronlevin.gitrev.gitHashShort
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws.WSAuthScheme
import play.api.libs.ws.ning.NingWSClient
import ru.kkmserver.protocol._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Api() {

  val wsClient = NingWSClient()

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

  def getRezult(request: GetRezultRequest): Future[GetRezultResponse] = {
    apiCall[GetRezultRequest, GetRezultResponse](request)
  }

  def getDataKKT(request: GetDataKKTRequest): Future[GetDataKKTResponse] = {
    apiCall[GetDataKKTRequest, GetDataKKTResponse](request)
  }

  def ofdReport(request: OfdReportRequest): Future[OfdReportResponse] = {
    apiCall[OfdReportRequest, OfdReportResponse](request)
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
          throw JsResultException(e.errors)
      }
    }
  }

  private def wsCall(json: JsValue) = {
    wsClient.url(Config.url)
      .withAuth(Config.username, Config.password, WSAuthScheme.BASIC)
      .withHeaders("Accept" -> "application/json")
      .post(json)
  }

  private val logger = Logger("kkmserver-api")

}

object Api {
  val theGitHash: String = gitHashShort
}
