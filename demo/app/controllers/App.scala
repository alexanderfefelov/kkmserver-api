package controllers

import org.apache.commons.lang3.StringEscapeUtils
import play.api.mvc._
import ru.kkmserver._
import ru.kkmserver.protocol._

import scala.concurrent.ExecutionContext.Implicits.global

object App extends Controller {

  val api = new Api()

  def index = Action { implicit request =>
    val title = "Настройки"
    val data = s"URL: ${Config.url}\nUsername: ${Config.username}\nPassword: ${Config.password}"
    Ok(views.html.data(title, data))
  }

  def list = Action.async { implicit request =>
    val future = api.list(ListRequest())
    for {
      obj <- future
    } yield {
      val title = "List"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def getDataKKT = Action.async { implicit request =>
    val future = api.getDataKKT(GetDataKKTRequest())
    for {
      obj <- future
    } yield {
      val title = "GetDataKKT"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def getRezultForm = Action { implicit request =>
    val title = "GetRezult"
    Ok(views.html.getRezultForm(title))
  }

  def getRezult = Action.async { implicit request =>
    val id = request.getQueryString("IdCommand").getOrElse("")
    val future = api.getRezult(GetRezultRequest(IdCommand = id))
    for {
      obj <- future
    } yield {
      val title = s"GetRezult $id"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def registerCheckSale = Action.async { implicit request =>
    val request = RegisterCheckRequest(
      IsFiscalCheck = true,
      TypeCheck = TypeCheckSale,
      CancelOpenedCheck = true,
      NotPrint = false,
      CashierName = "Швейк Йозеф",
      CheckStrings = List(
        PrintTextCheckString(PrintTextCheckStringData("Donec pretium est ac ante tincidunt blandit")),
        RegisterCheckString(RegisterCheckStringData("Планшет PRESTIGIO MultiPad 3147 3g, 1GB, 8GB, 3G, Android 6.0", Quantity = 1, Price = 1, Amount = 1))
      ),
      Cash = 1.0
    )
    val future = api.registerCheck(request)
    for {
      obj <- future
    } yield {
      val title = "RegisterCheck (sale)"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def registerCheckSaleReturn = Action.async { implicit request =>
    val request = RegisterCheckRequest(
      IsFiscalCheck = true,
      TypeCheck = TypeCheckSaleReturn,
      CancelOpenedCheck = true,
      NotPrint = false,
      CashierName = "Швейк Йозеф",
      CheckStrings = List(
        PrintTextCheckString(PrintTextCheckStringData("Lorem ipsum dolor sit amet")),
        RegisterCheckString(RegisterCheckStringData("Планшет PRESTIGIO MultiPad 3147 3g, 1GB, 8GB, 3G, Android 6.0", Quantity = 1, Price = 1, Amount = 1))
      ),
      Cash = 1.0
    )
    val future = api.registerCheck(request)
    for {
      obj <- future
    } yield {
      val title = "RegisterCheck (sale return)"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def xReport = Action.async { implicit request =>
    val future = api.xReport(XReportRequest())
    for {
      obj <- future
    } yield {
      val title = "XReport"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def zReport = Action.async { implicit request =>
    val future = api.zReport(ZReportRequest())
    for {
      obj <- future
    } yield {
      val title = "ZReport"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

  def ofdReport = Action.async { implicit request =>
    val future = api.ofdReport(OfdReportRequest())
    for {
      obj <- future
    } yield {
      val title = "OfdReport"
      val data = StringEscapeUtils.unescapeJava(pprint.apply(obj).plainText)
      Ok(views.html.data(title, data))
    }
  }

}
