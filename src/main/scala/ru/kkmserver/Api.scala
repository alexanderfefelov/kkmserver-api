package ru.kkmserver

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json._
import play.api.libs.ws.WSAuthScheme
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import ru.kkmserver.protocol._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Api()(implicit val actorSystem: ActorSystem, implicit val actorMaterializer: ActorMaterializer) {

  val wsClient = StandaloneAhcWSClient()

  def list(request: ListRequest): Future[ListResponse] = {
    val requestJson = Json.toJson(request)
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      response.json.validate[ListResponse] match {
        case s: JsSuccess[ListResponse] =>
          s.value
        case e: JsError =>
          throw JsResultException(e.errors)
      }
    }
  }

  def xReport(request: XReportRequest): Future[XReportResponse] = {
    val requestJson = Json.toJson(request)
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      response.json.validate[XReportResponse] match {
        case s: JsSuccess[XReportResponse] =>
          s.value
        case e: JsError =>
          throw JsResultException(e.errors)
      }
    }
  }

  def zReport(request: ZReportRequest): Future[ZReportResponse] = {
    val requestJson = Json.toJson(request)
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      response.json.validate[ZReportResponse] match {
        case s: JsSuccess[ZReportResponse] =>
          s.value
        case e: JsError =>
          throw JsResultException(e.errors)
      }
    }
  }

  def registerCheck(request: RegisterCheckRequest): Future[RegisterCheckResponse] = {
    val requestJson = Json.toJson(request)
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      response.json.validate[RegisterCheckResponse] match {
        case s: JsSuccess[RegisterCheckResponse] =>
          s.value
        case e: JsError =>
          throw JsResultException(e.errors)
      }
    }
  }

  def getRezult(request: GetRezultRequest): Future[GetRezultResponse] = {
    val requestJson = Json.toJson(request)
    val responseFuture = call(requestJson)
    for {
      response <- responseFuture
    } yield {
      response.json.validate[GetRezultResponse] match {
        case s: JsSuccess[GetRezultResponse] =>
          s.value
        case e: JsError =>
          throw JsResultException(e.errors)
      }
    }
  }

  private def call(json: JsValue) = {
    wsClient.url(Config.url)
      .withAuth(Config.username, Config.password, WSAuthScheme.BASIC)
      .withHttpHeaders("Accept" -> "application/json")
      .post(json)
  }

}
