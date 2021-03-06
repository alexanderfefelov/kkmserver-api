package controllers

import play.api.mvc._
import com.github.alexanderfefelov.kkmserver.api._
import com.github.alexanderfefelov.kkmserver.api.protocol._
import sext._

import scala.concurrent.ExecutionContext.Implicits.global

object App extends Controller {

  val DEVICE = 1
  val CASHIER_NAME = "Швейк Йозеф"
  val CASHIER_VATIN = "987654321098"

  val api = new KkmServerApi()

  def index = Action { implicit request =>
    val title = "Настройки"
    val data = s"URL: ${KkmServerApiConfig.kkmServerUrl}\nUsername: ${KkmServerApiConfig.kkmServerUsername}\nPassword: ${KkmServerApiConfig.kkmServerPassword}"
    Ok(views.html.data(title, data))
  }

  def getServerData = Action.async { implicit request =>
    val future = api.getServerData(GetServerDataRequest())
    for {
      obj <- future
    } yield {
      val title = "GetServerData"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def list = Action.async { implicit request =>
    val future = api.list(ListRequest())
    for {
      obj <- future
    } yield {
      val title = "List"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def getDataKKT = Action.async { implicit request =>
    val future = api.getDataKKT(GetDataKKTRequest(NumDevice = DEVICE))
    for {
      obj <- future
    } yield {
      val title = "GetDataKKT"
      val data = obj.valueTreeString
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
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def registerCheckSale = Action.async { implicit request =>
    val request = createRegisterCheckRequest(CHECK_TYPE_SALE)
    val future = api.registerCheck(request)
    for {
      obj <- future
    } yield {
      val title = "RegisterCheck (sale)"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def registerCheckSaleReturn = Action.async { implicit request =>
    val request = createRegisterCheckRequest(CHECK_TYPE_SALE_RETURN)
    val future = api.registerCheck(request)
    for {
      obj <- future
    } yield {
      val title = "RegisterCheck (sale return)"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def xReport = Action.async { implicit request =>
    val future = api.xReport(XReportRequest(NumDevice = DEVICE))
    for {
      obj <- future
    } yield {
      val title = "XReport"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def zReport = Action.async { implicit request =>
    val future = api.zReport(ZReportRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN))
    for {
      obj <- future
    } yield {
      val title = "ZReport"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  def ofdReport = Action.async { implicit request =>
    val future = api.ofdReport(OfdReportRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME))
    for {
      obj <- future
    } yield {
      val title = "OfdReport"
      val data = obj.valueTreeString
      Ok(views.html.data(title, data))
    }
  }

  private def createRegisterCheckRequest(typeCheck: Int) = {
    RegisterCheckRequest(
      NumDevice = Option(DEVICE),
      IsFiscalCheck = true,
      TypeCheck = typeCheck,
      CancelOpenedCheck = true,
      CashierName = CASHIER_NAME,
      CashierVATIN = CASHIER_VATIN,
      CheckStrings = List(
        PrintTextCheckString(PrintTextCheckStringData("Donec pretium est ac ante tincidunt blandit")),
        RegisterCheckString(RegisterCheckStringData("Планшет PRESTIGIO MultiPad 3147 3g, 1GB, 8GB, 3G, Android 6.0", Quantity = 1.00, Price = 1.00, Amount = 1.00))
      ),
      Cash = 1.00
    )
  }

}
