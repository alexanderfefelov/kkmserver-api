package ru.kkmserver

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
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      logger.debug(s"response: ${response.json}")
      response.json.validate[ListResponse] match {
        case s: JsSuccess[ListResponse] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          throw JsResultException(e.errors)
      }
    }
  }

  def xReport(request: XReportRequest): Future[XReportResponse] = {
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      logger.debug(s"response: ${response.json}")
      response.json.validate[XReportResponse] match {
        case s: JsSuccess[XReportResponse] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          throw JsResultException(e.errors)
      }
    }
  }

  def zReport(request: ZReportRequest): Future[ZReportResponse] = {
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      logger.debug(s"response: ${response.json}")
      response.json.validate[ZReportResponse] match {
        case s: JsSuccess[ZReportResponse] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          throw JsResultException(e.errors)
      }
    }
  }

  def registerCheck(request: RegisterCheckRequest): Future[RegisterCheckResponse] = {
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      logger.debug(s"response: ${response.json}")
      response.json.validate[RegisterCheckResponse] match {
        case s: JsSuccess[RegisterCheckResponse] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          throw JsResultException(e.errors)
      }
    }
  }

  def getRezult(request: GetRezultRequest): Future[GetRezultResponse] = {
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      logger.debug(s"response: ${response.json}")
      response.json.validate[GetRezultResponse] match {
        case s: JsSuccess[GetRezultResponse] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          throw JsResultException(e.errors)
      }
    }
  }

  def getDataKKT(request: GetDataKKTRequest): Future[GetDataKKTResponse] = {
    val requestJson = Json.toJson(request)
    logger.debug(s"request: $requestJson")
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      logger.debug(s"response: ${response.json}")
      response.json.validate[GetDataKKTResponse] match {
        case s: JsSuccess[GetDataKKTResponse] =>
          s.value
        case e: JsError =>
          logger.error(s"error: ${e.errors}")
          throw JsResultException(e.errors)
      }
    }
  }

  private def call(json: JsValue) = {
    wsClient.url(Config.url)
      .withAuth(Config.username, Config.password, WSAuthScheme.BASIC)
      .withHeaders("Accept" -> "application/json")
      .post(json)
  }

  private val logger = Logger("kkmserver-api")

}
