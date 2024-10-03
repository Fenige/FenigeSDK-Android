package com.sdk.fenigepaytool.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.sdk.fenigepaytool.api.Api
import com.sdk.fenigepaytool.api.Api.Companion.getTransactionUrl
import com.sdk.fenigepaytool.api.request.GpayRequest
import com.sdk.fenigepaytool.api.request.Language
import com.sdk.fenigepaytool.api.request.PaytoolRequest
import com.sdk.fenigepaytool.api.request.Token
import com.sdk.fenigepaytool.api.request.TypeOfAuthorization
import com.sdk.fenigepaytool.databinding.ActivityPaytoolBinding
import com.sdk.fenigepaytool.di.coreModule
import com.sdk.fenigepaytool.entity.Config
import com.sdk.fenigepaytool.entity.PaytoolResultCallback
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender
import org.koin.dsl.koinApplication


internal class PaytoolActivity: AppCompatActivity() {

    companion object {
        private const val REQUEST_OBJECT_KEY = "request_object_key"
        private const val API_KEY_OBJECT_KEY = "api_key_object_key"
        private const val GPAY_TOKEN_OBJECT_KEY = "token_object_key"
        private const val EMAIL_OBJECT_KEY = "email_object_key"
        private lateinit var mPaytoolResultCallback: PaytoolResultCallback

        fun launch(
            activity: Activity, apiKey: String, currencyCode: String,
            amount: Int, description: String,
            merchantUrl: String, orderNumber: String,
            formLanguage: String, redirectUrl: RedirectUrl,
            sender: Sender, transactionId: String,
            autoClear: Boolean, isRecurring: Boolean, config: Config,
            paytoolResultCallback: PaytoolResultCallback,
        ) {
            val intent = Intent(activity, PaytoolActivity::class.java)
            Api.config = config
            val typeOfAuthorization = TypeOfAuthorization.getType(isRecurring).name
            val paytoolRequest = PaytoolRequest(
                transactionId = transactionId,
                currencyCode = currencyCode,
                amount = amount,
                description = description,
                merchantUrl = merchantUrl,
                orderNumber = orderNumber,
                formLanguage = formLanguage,
                redirectUrl = redirectUrl,
                sender = sender,
                typeOfAuthorization = typeOfAuthorization,
                autoClear = autoClear
            )
            mPaytoolResultCallback = paytoolResultCallback
            val bundle = Bundle()
            bundle.putParcelable(REQUEST_OBJECT_KEY, paytoolRequest)
            bundle.putString(API_KEY_OBJECT_KEY, apiKey)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }

        fun launchWithGpay(
            activity: Activity, apiKey: String,
            token: String, email: String,
            currencyCode: String,
            amount: Int, description: String,
            merchantUrl: String, orderNumber: String,
            formLanguage: String, redirectUrl: RedirectUrl,
            sender: Sender, transactionId: String,
            autoClear: Boolean, isRecurring: Boolean, config: Config,
            paytoolResultCallback: PaytoolResultCallback,
        ) {
            val intent = Intent(activity, PaytoolActivity::class.java)
            Api.config = config
            val typeOfAuthorization = TypeOfAuthorization.getType(isRecurring).name

            val paytoolRequest = PaytoolRequest(
                transactionId = transactionId,
                currencyCode = currencyCode,
                amount = amount,
                description = description,
                merchantUrl = merchantUrl,
                orderNumber = orderNumber,
                formLanguage = formLanguage,
                redirectUrl = redirectUrl,
                sender = sender,
                typeOfAuthorization = typeOfAuthorization,
                autoClear = autoClear
            )
            mPaytoolResultCallback = paytoolResultCallback
            val bundle = Bundle()
            bundle.putParcelable(REQUEST_OBJECT_KEY, paytoolRequest)
            bundle.putString(API_KEY_OBJECT_KEY, apiKey)
            bundle.putString(GPAY_TOKEN_OBJECT_KEY, token)
            bundle.putString(EMAIL_OBJECT_KEY, email)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    val koinApp = koinApplication {
        modules(coreModule)
    }

    private val viewModel: PaytoolViewModel = koinApp.koin.get()
    private var transactionId: String = ""

    private val binding: ActivityPaytoolBinding by lazy {
        ActivityPaytoolBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObservers()
        setOnClickListener()
        viewModel.getTransactionId(getApiKey(), getPaytoolRequest())
    }

    private fun initObservers() {
        observe(viewModel.error) {
            onTransactionFinish()
        }
        observe(viewModel.transactionId) {
            transactionId = it
            val token = getGpayToken()
            if(token.isNullOrEmpty()) initWebView(it)
            else executeGooglePay()
        }
    }

    private fun executeGooglePay() {
        val paytoolRequest = getPaytoolRequest()
        val token = Gson().fromJson(getGpayToken().orEmpty(), Token::class.java)
        val request = GpayRequest(
            transactionId = transactionId,
            sender = com.sdk.fenigepaytool.api.request.Sender(
                firstName = paytoolRequest.sender.firstName,
                lastName = paytoolRequest.sender.lastName,
                email = getEmail()
            ),
            token = token,
            language = Language(paytoolRequest.formLanguage)
        )
        viewModel.googlePay(request)
    }

    private fun setOnClickListener() {
        binding.back.setOnClickListener {
            onTransactionFinish()
        }
    }

    private fun getPaytoolRequest() =
        intent.getParcelableExtra<PaytoolRequest>(REQUEST_OBJECT_KEY)!!

    private fun getApiKey() = intent.getStringExtra((API_KEY_OBJECT_KEY))!!

    private fun getGpayToken() : String? = intent.getStringExtra((GPAY_TOKEN_OBJECT_KEY))

    private fun getEmail() = intent.getStringExtra((EMAIL_OBJECT_KEY))!!

    private fun initWebView(transactionId: String) {
        binding.progress.visibility = View.VISIBLE
        val redirectUrl = getPaytoolRequest().redirectUrl
        with(binding.webView) {
            visibility = View.GONE
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String?) {
                    url?.let {
                        visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        if (it.contains(redirectUrl.successUrl) || it.contains(redirectUrl.failureUrl))
                            onTransactionFinish()
                    }
                }
            }
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(getTransactionUrl() + transactionId)
        }
    }

    private fun onTransactionFinish() {
        mPaytoolResultCallback.onFinishCallback(transactionId)
        finish()
    }

    private fun <T> observe(liveData: LiveData<T>, observer: Observer<T>) {
        liveData.observe(this, observer)
    }
}