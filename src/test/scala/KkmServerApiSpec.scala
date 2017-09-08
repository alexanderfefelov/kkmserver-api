import org.scalatest._
import com.github.alexanderfefelov.kkmserver.api.KkmServerApi
import com.github.alexanderfefelov.kkmserver.api.protocol._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class KkmServerApiSpec extends AsyncFlatSpec {

  private val api = new KkmServerApi()

  private val CASHIER_NAME = "Швейк Йозеф"
  private val CASHIER_VATIN = "987654321098"

  private var commandId = ""

  for (device <- List(2, 3)) { // В виртуальной машине extra/kkmserver/vagrant устройство 2 поддерживает ФФД 1.0, а устройство 3 - ФФД 1.05

    s"List ($device)" should "run without error, provide valid metadata, and return exactly 3 devices" in {
      val request = ListRequest()
      api.list(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
        assert(response.ListUnit.size == 3)
      }
    }

    s"GetDataKKT ($device)" should "run without error and provide valid metadata" in {
      val request = GetDataKKTRequest(NumDevice = device)
      api.getDataKKT(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"OfdReport ($device)" should "run without error and provide valid metadata" in {
      val request = OfdReportRequest(NumDevice = device, CashierName = CASHIER_NAME)
      api.ofdReport(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"OpenShift ($device)" should "run without error and provide valid metadata" in {
      val request = OpenShiftRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
      api.openShift(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"OpenCashDrawer ($device)" should "run without error and provide valid metadata" in {
      val request = OpenCashDrawerRequest(NumDevice = device, CashierName = CASHIER_NAME)
      api.openCashDrawer(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"DepositingCash ($device)" should "run without error and provide valid metadata" in {
      val request = DepositingCashRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN, Amount = 1.00)
      api.depositingCash(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"PaymentCash ($device)" should "run without error and provide valid metadata" in {
      val request = PaymentCashRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN, Amount = 1.00)
      api.paymentCash(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"RegisterCheck ($device)" should "run without error and provide valid metadata" in {
      val request = createRegisterCheckRequest(device, CHECK_TYPE_SALE)
      api.registerCheck(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"XReport ($device)" should "run without error and provide valid metadata" in {
      val request = XReportRequest(NumDevice = device)
      api.xReport(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"CloseShift ($device)" should "run without error and provide valid metadata" in {
      val request = CloseShiftRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
      api.closeShift(request) map { response =>
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"ZReport ($device)" should "run without error and provide valid metadata" in {
      openShift(device)
      val request = ZReportRequest(NumDevice = device, CashierName = CASHIER_NAME, CashierVATIN = CASHIER_VATIN)
      api.zReport(request) map { response =>
        commandId = response.IdCommand
        assert(response.Status == 0)
        assert(response.Error.isEmpty)
        assert(response.Command == request.Command)
        assert(response.IdCommand == request.IdCommand)
      }
    }

    s"GetRezult ($device)" should "run without error and provide valid metadata" in {
      val request = GetRezultRequest(IdCommand = commandId)
      api.getRezult(request) map { response =>
        assert(response.Status == 0)
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

  private def createRegisterCheckRequest(device: Int, typeCheck: Int): RegisterCheckRequest = {
    RegisterCheckRequest(
      NumDevice = Option(device),
      IsFiscalCheck = true,
      TypeCheck = typeCheck,
      CancelOpenedCheck = true,
      NotPrint = false,
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
