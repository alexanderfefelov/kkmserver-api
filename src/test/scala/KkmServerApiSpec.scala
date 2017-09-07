import org.scalatest._
import com.github.alexanderfefelov.kkmserver.api.KkmServerApi
import com.github.alexanderfefelov.kkmserver.api.protocol._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class KkmServerApiSpec extends AsyncFlatSpec {

  private val api = new KkmServerApi()

  private val DEVICE = 2
  private val CASHIER_NAME = "Швейк Йозеф"

  private var commandId = ""

  "List" should "run without error and return exactly 3 devices" in {
    val request = ListRequest()
    api.list(request) map { response =>
      assert(response.Status == 0)
      assert(response.ListUnit.size == 3)
    }
  }

  "GetDataKKT" should "run without error" in {
    val request = GetDataKKTRequest(NumDevice = DEVICE)
    api.getDataKKT(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "OfdReport" should "run without error" in {
    val request = OfdReportRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME)
    api.ofdReport(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "OpenShift" should "run without error" in {
    openShift() map { response =>
      assert(response.Status == 0)
    }
  }

  "OpenCashDrawer" should "run without error" in {
    val request = OpenCashDrawerRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME)
    api.openCashDrawer(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "DepositingCash" should "run without error" in {
    val request = DepositingCashRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME, Amount = 1.00)
    api.depositingCash(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "PaymentCash" should "run without error" in {
    val request = PaymentCashRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME, Amount = 1.00)
    api.paymentCash(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "RegisterCheck" should "run without error" in {
    val request = createRegisterCheckRequest(CHECK_TYPE_SALE)
    api.registerCheck(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "XReport" should "run without error" in {
    val request = XReportRequest(NumDevice = DEVICE)
    api.xReport(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "CloseShift" should "run without error" in {
    openShift()
    val request = CloseShiftRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME)
    api.closeShift(request) map { response =>
      assert(response.Status == 0)
    }
  }

  "ZReport" should "run without error" in {
    openShift()
    val request = ZReportRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME)
    api.zReport(request) map { response =>
      commandId = response.IdCommand
      assert(response.Status == 0)
    }
  }

  "GetRezult" should "run without error" in {
    val request = GetRezultRequest(IdCommand = commandId)
    api.getRezult(request) map { response =>
      assert(response.Status == 0)
    }
  }

  private def openShift(): Future[OpenShiftResponse] = {
    val request = OpenShiftRequest(NumDevice = DEVICE, CashierName = CASHIER_NAME)
    api.openShift(request)
  }

  private def createRegisterCheckRequest(typeCheck: Int): RegisterCheckRequest = {
    RegisterCheckRequest(
      NumDevice = Option(DEVICE),
      IsFiscalCheck = true,
      TypeCheck = typeCheck,
      CancelOpenedCheck = true,
      NotPrint = false,
      CashierName = CASHIER_NAME,
      CheckStrings = List(
        PrintTextCheckString(PrintTextCheckStringData("Donec pretium est ac ante tincidunt blandit")),
        RegisterCheckString(RegisterCheckStringData("Планшет PRESTIGIO MultiPad 3147 3g, 1GB, 8GB, 3G, Android 6.0", Quantity = 1.00, Price = 1.00, Amount = 1.00))
      ),
      Cash = 1.00
    )
  }

}
