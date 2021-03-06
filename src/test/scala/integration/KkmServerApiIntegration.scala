/*
 * Copyright (c) 2017-2019 Alexander Fefelov <alexanderfefelov@yandex.ru>
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

package integration

import com.auvik.util.SequentialExecutionContext
import com.github.alexanderfefelov.kkmserver.api._
import com.github.alexanderfefelov.kkmserver.api.protocol._
import org.scalatest._
import org.scalatest.concurrent._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class KkmServerApiIntegration extends AsyncFlatSpec  with PatienceConfiguration {

  override implicit val executionContext: ExecutionContext = SequentialExecutionContext(scala.concurrent.ExecutionContext.Implicits.global)

  private val api = new KkmServerApi()

  private val UNKNOWN_COMMAND = "foobarbaz"
  private val CASHIER_NAME = "Швейк Йозеф"
  private val CASHIER_VATIN = "987654321098"
  private val TEXT = "Donec pretium est ac ante tincidunt blandit"
  private val IMAGE = "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"
  private val GOOD = "Планшет PRESTIGIO MultiPad 3147 3g, 1GB, 8GB, 3G, Android 6.0"
  private val EAN = "8595248136472"

  private var commandId = ""

  "KkmServerApiConfig" should "load correct values" in {
    val config = KkmServerApiConfig
    assert(config.kkmServerUrl == "http://localhost:5893/Execute/sync")
    assert(config.kkmServerUsername == "User")
    assert(config.kkmServerPassword == "user")
    assert(config.kkmServerConnectTimeout == 20000)
    assert(config.kkmServerReadTimeout == 20000)
    assert(config.graphiteEnabled == false)
    assert(config.graphiteHost == "localhost")
    assert(config.graphitePort == 2003)
    assert(config.graphitePrefix == "test")
  }

  "GetDataKKT, unknown device" should "throw exception with known message string" in {
    val request = GetDataKKTRequest(NumDevice = 9)
    val responseFuture = api.getDataKKT(request)
    ScalaFutures.whenReady(responseFuture.failed, timeout(5.seconds), interval(500.millis)) { e =>
      assert(e.isInstanceOf[KkmServerApiException])
      assert(e.getMessage.startsWith("Устройство (с параметрами NumDevice=9) не найдено: не настроено или отключено."))
    }
  }

  "Unknown command" should "throw exception with known message string" in {
    val request = ListRequest(Command = UNKNOWN_COMMAND)
    val responseFuture = api.list(request)
    ScalaFutures.whenReady(responseFuture.failed) { e =>
      assert(e.isInstanceOf[KkmServerApiException])
      assert(e.getMessage.startsWith("501 Not Implemented"))
    }
  }

  for (active <- List(false, true)) {

    s"OnOffUnut, active: $active" should "run without error and provide valid metadata" in {
      val request = OnOffUnutRequest(NumDevice = 1, Active = active)
      api.onOffUnut(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    if (!active) {
      "GetDataKKT, inactive device" should "throw exception with known message string" in {
        val request = GetDataKKTRequest(NumDevice = 1)
        val responseFuture = api.getDataKKT(request)
        ScalaFutures.whenReady(responseFuture.failed) { e =>
          assert(e.isInstanceOf[KkmServerApiException])
          assert(e.getMessage.startsWith("Устройство (с параметрами NumDevice=1) не найдено: не настроено или отключено."))
        }
      }

    }

  } // active

  "GetServerData" should "run without error and provide valid metadata" in {
    val request = GetServerDataRequest()
    api.getServerData(request) map { response =>
      assert(response.Status == COMMAND_STATUS_OK)
      assert(response.Error.isEmpty)
      assert(response.Command == request.Command)
      assert(response.IdCommand == request.IdCommand)
    }
  }

  "List" should "run without error, provide valid metadata, and return exactly 3 devices" in {
    val request = ListRequest()
    api.list(request) map { response =>
      assert(response.Status == COMMAND_STATUS_OK)
      assert(response.Error.isEmpty)
      assert(response.Command == request.Command)
      assert(response.IdCommand == request.IdCommand)
      assert(response.ListUnit.size == 3)
    }
  }

  for (device <- List(2, 3)) { // В виртуальной машине extra/kkmserver/vagrant устройство 2 поддерживает ФФД 1.0, а устройство 3 - ФФД 1.05

    s"GetDataKKT, device: $device" should "run without error and provide valid metadata" in {
      val request = GetDataKKTRequest(NumDevice = device)
      api.getDataKKT(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"OfdReport, device: $device" should "run without error and provide valid metadata" in {
      val request = OfdReportRequest(NumDevice = device, CashierName = CASHIER_NAME)
      api.ofdReport(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"OpenShift, device: $device" should "run without error and provide valid metadata" in {
      val request = OpenShiftRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
      api.openShift(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"OpenCashDrawer, device: $device" should "run without error and provide valid metadata" in {
      val request = OpenCashDrawerRequest(NumDevice = device, CashierName = CASHIER_NAME)
      api.openCashDrawer(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"DepositingCash, device: $device" should "run without error and provide valid metadata" in {
      val request = DepositingCashRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN, Amount = 1.00)
      api.depositingCash(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"PaymentCash, device: $device" should "run without error and provide valid metadata" in {
      val request = PaymentCashRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN, Amount = 1.00)
      api.paymentCash(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    for (taxVariant <- List(None, Some(SYSTEM_OF_TAXATION_0), Some(SYSTEM_OF_TAXATION_1), Some(SYSTEM_OF_TAXATION_2), Some(SYSTEM_OF_TAXATION_3), Some(SYSTEM_OF_TAXATION_4), Some(SYSTEM_OF_TAXATION_5))) {
      for (checkType <- List(CHECK_TYPE_SALE, CHECK_TYPE_SALE_RETURN, CHECK_TYPE_PURCHASE_RETURN, CHECK_TYPE_PURCHASE)) {
        for (isFiscal <- List(true, false)) {
          for (tax <- List(VAT_NO, VAT_0, VAT_10, VAT_18, VAT_110, VAT_118)) {
            for (department <- 0 to 4) {
              device match {

                case 2 => // ФФД 1.0
                  s"RegisterCheck10, device: $device taxVariant: $taxVariant checkType: $checkType fiscal: $isFiscal tax: $tax department: $department" should "run without error and provide valid metadata" in {
                    val request = createRegisterCheckRequest10(device, taxVariant, checkType, isFiscal, tax, department)
                    api.registerCheck10(request) map { response =>
                      assert(response.Status == COMMAND_STATUS_OK)
                      assert(response.Error.isEmpty)
                      assert(response.Command == request.Command)
                      assert(response.IdCommand == request.IdCommand)
                      assert(Option(response.NumDevice) == request.NumDevice)
                    }
                  }

                case 3 => // ФФД 1.05
                  s"RegisterCheck, device: $device taxVariant: $taxVariant checkType: $checkType fiscal: $isFiscal tax: $tax department: $department" should "run without error and provide valid metadata" in {
                    val request = createRegisterCheckRequest(device, taxVariant, checkType, isFiscal, tax, department)
                    api.registerCheck(request) map { response =>
                      assert(response.Status == COMMAND_STATUS_OK)
                      assert(response.Error.isEmpty)
                      assert(response.Command == request.Command)
                      assert(response.IdCommand == request.IdCommand)
                      assert(Option(response.NumDevice) == request.NumDevice)
                    }
                  }

              }
            } // department
          } // tax
        } // isFiscal
      } // checkType
    } // taxVariant

    s"GetDataCheck, device: $device" should "run without error and provide valid metadata" in {
      val request = GetDataCheckRequest(NumDevice = device, FiscalNumber = 0, NumberCopies = 1)
      api.getDataCheck(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"GetDataCheck, unknown check, device: $device" should "throw exception with known message string" in {
      val request = GetDataCheckRequest(NumDevice = device, FiscalNumber = -1, NumberCopies = 1)
      val responseFuture = api.getDataCheck(request)
      ScalaFutures.whenReady(responseFuture.failed, timeout(5.seconds), interval(500.millis)) { e =>
        assert(e.isInstanceOf[KkmServerApiException])
        assert(e.getMessage.startsWith("Не удалось получить данные чека"))
      }
    }

    s"XReport, device: $device" should "run without error and provide valid metadata" in {
      val request = XReportRequest(NumDevice = device)
      api.xReport(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"CloseShift, device: $device" should "run without error and provide valid metadata" in {
      val request = CloseShiftRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
      api.closeShift(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"ZReport, device: $device" should "run without error and provide valid metadata" in {
      openShift(device)
      val request = ZReportRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
      api.zReport(request) map { response =>
        commandId = response.IdCommand
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.NumDevice == request.NumDevice)
      }
    }

    s"GetRezult, device: $device" should "run without error and provide valid metadata" in {
      val request = GetRezultRequest(IdCommand = commandId)
      api.getRezult(request) map { response =>
        assert(response.Status == COMMAND_STATUS_OK)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand.isEmpty)
      }
    }

  }

  private def openShift(device: Int): Future[OpenShiftResponse] = {
    val request = OpenShiftRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
    api.openShift(request)
  }

  private def createRegisterCheckRequest(device: Int, taxVariant: Option[String], checkType: Int, isFiscal: Boolean, tax: Int, department: Int): RegisterCheckRequest = {
    RegisterCheckRequest(
      NumDevice = Option(device),
      IsFiscalCheck = isFiscal,
      TypeCheck = checkType,
      TaxVariant = taxVariant,
      CashierName = CASHIER_NAME,
      CashierVATIN = CASHIER_VATIN,
      ClientInfo = Option("ООО \"Рога и копыта\""),
      ClientINN = Option("1234567890"),
      SenderEmail = Option("none@inter.net"),
      AdditionalProps = List(
        AdditionalProp(Print = true, PrintInHeader = true, NameProp = "Название1", Prop = "Значение1"),
        AdditionalProp(Print = true, PrintInHeader = false, NameProp = "Название2", Prop = "Значение2")
      ),
      CheckStrings = List(
        PrintTextCheckString(PrintTextCheckStringData(TEXT)),
        PrintImageCheckString(PrintImageCheckStringData(IMAGE)),
        RegisterCheckString(RegisterCheckStringData(GOOD, EAN13 = Option(EAN), Quantity = 5.00, Price = 4000.00, Amount = 19500.00, Tax = tax, Department = department)),
        BarCodeCheckString(BarCodeCheckStringData("EAN13", EAN))
      ),
      Payment = PaymentData(Cash = 19000.00, ElectronicPayment = 500.00)
    )
  }

  private def createRegisterCheckRequest10(device: Int, taxVariant: Option[String], checkType: Int, isFiscal: Boolean, tax: Int, department: Int): RegisterCheckRequest10 = {
    RegisterCheckRequest10(
      NumDevice = Option(device),
      IsFiscalCheck = isFiscal,
      TypeCheck = checkType,
      TaxVariant = taxVariant,
      CashierName = CASHIER_NAME,
      AdditionalProps = List(
        AdditionalProp10(Print = true, PrintInHeader = true, NameProp = "Название1", Prop = "Значение1"),
        AdditionalProp10(Print = true, PrintInHeader = false, NameProp = "Название2", Prop = "Значение2")
      ),
      CheckStrings = List(
        PrintTextCheckString10(PrintTextCheckStringData10(TEXT)),
        PrintImageCheckString10(PrintImageCheckStringData10(IMAGE)),
        RegisterCheckString10(RegisterCheckStringData10(GOOD, EAN13 = Option(EAN), Quantity = 5.00, Price = 4000.00, Amount = 19500.00, Tax = tax, Department = department)),
        BarCodeCheckString10(BarCodeCheckStringData10("EAN13", EAN))
      ),
      Cash = 19000.00,
      CashLessType1 = 500.00
    )
  }

}
