package com.example.fenigesdkexample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fenigesdkexample.utils.Constants
import com.example.fenigesdkexample.utils.PaymentsUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

abstract class GPayActivity : AppCompatActivity(), GooglePayListener {

    /**
     * A client for interacting with the Google Pay API.
     *
     * @see [PaymentsClient](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient)
     */
    private lateinit var paymentsClient: PaymentsClient

    /**
     * Arbitrarily-picked constant integer you define to track a request for payment data activity.
     *
     * @value #LOAD_PAYMENT_DATA_REQUEST_CODE
     */
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    /**
     * Initialize the Google Pay API on creation of the activity
     *
     * @see Activity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPaymentClient()
    }

    // Initialize a Google Pay API client for an environment suitable for testing.
    // It's recommended to create the PaymentsClient object inside of the onCreate method.
    private fun createPaymentClient() {
        try {
            paymentsClient = PaymentsUtil.createPaymentsClient(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Determine the viewer's ability to pay with a payment method supported by your app and display a
     * Google Pay payment button.
     *
     * @see [](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient.html.isReadyToPay
    ) */
    protected fun possiblyShowGooglePayButton(summ: Int, currency: String) {
        val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString())
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let{
                    setGooglePayAvailable(it, summ, currency)
                }
            } catch (exception: ApiException) {
                // Process error
                Timber.tag("isReadyToPay failed").w(exception)
            }
        }
    }

    /**
     * If isReadyToPay returned `true`, show the button and hide the "checking" text. Otherwise,
     * notify the user that Google Pay is not available. Please adjust to fit in with your current
     * user flow. You are not required to explicitly let the user know if isReadyToPay returns `false`.
     *
     * @param available isReadyToPay API response.
     */
    private fun setGooglePayAvailable(available: Boolean, summ: Int, currency: String) {
        if (available)
            requestPayment(summ, currency)
        else
            Toast.makeText(this, "Google Pay not available", Toast.LENGTH_SHORT).show()
    }

    private fun openGooglePayInMarketAndFinish() {
        try {
            val intent =
                packageManager.getLaunchIntentForPackage(Constants.GOOGLE_PAY_PACKAGE_NAME)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent?.let { startActivity(it) }
        } catch (_: java.lang.Exception) { }
    }

    private fun requestPayment(summ: Int, currency: String) {
        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(
            summ,
            currency,
        )

        if (paymentDataRequestJson == null) {
            Toast.makeText(
                applicationContext,
                "Can't fetch payment data request",
                Toast.LENGTH_LONG
            ).show()
            Timber.tag("RequestPayment").e("Can't fetch payment data request")
            return
        }
        Timber.tag("GooglePaymentToken").d(paymentDataRequestJson.toString())
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(
            paymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE
        )
    }

    /**
     * Handle a resolved activity from the Google Pay payment sheet.
     *
     * @param requestCode Request code originally supplied to AutoResolveHelper in requestPayment().
     * @param resultCode Result code returned by the Google Pay API.
     * @param data Intent from the Google Pay API containing payment or error data.
     * @see [Getting a result
     * from an Activity](https://developer.android.com/training/basics/intents/result)
     */
    @SuppressLint("MissingSuperCall")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.tag("GooglePaymentToken").d("onActivityResult")
        Timber.tag("GooglePaymentToken").d("onActivityResult requestCode=%s", requestCode)
        when (requestCode) {
            // Value passed in AutoResolveHelper
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        }

                    RESULT_CANCELED -> {
                        Toast.makeText(this, "RESULT_CANCELED", Toast.LENGTH_SHORT).show()
                    }

                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            Toast.makeText(this, "RESULT_ERROR", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing and shipping address.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     * @see [Payment
     * Data](https://developers.google.com/pay/api/android/reference/object.PaymentData)
     */
    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson()

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData = JSONObject(paymentInformation)
                .getJSONObject("paymentMethodData")
          /*  val shippingAddress = JSONObject(paymentInformation)
                .getJSONObject("shippingAddress")

            val email = JSONObject(paymentInformation)
                .getString("email")

            val name = shippingAddress
                .getString("name")
            val phoneNumber = shippingAddress
                .getString("phoneNumber")

            val countryCode = shippingAddress
                .getString("countryCode")
            val address1 = shippingAddress
                .getString("address1")
            val address2 = shippingAddress
                .getString("address2")
            val street = "$address1 $address2"
            val postalCode = shippingAddress
                .getString("postalCode")
            val city = shippingAddress
                .getString("locality")
            val description = paymentMethodData
                .getString("description")*/

            val token = paymentMethodData
                .getJSONObject("tokenizationData")
                .getString("token")


            onResult(token)
        } catch (e: JSONException) {
            Timber.e("Error: $e")
        }
    }
}

interface GooglePayListener {
    fun onResult(token: String)
}