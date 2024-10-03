package com.example.fenigesdkexample

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.fenigeexample.R
import com.example.fenigeexample.databinding.ActivityMainBinding
import com.example.fenigesdkexample.utils.PaymentsUtil
import com.google.android.gms.wallet.button.ButtonConstants
import com.google.android.gms.wallet.button.ButtonOptions
import com.sdk.fenigepaytool.FenigePaytool
import com.sdk.fenigepaytool.entity.Address
import com.sdk.fenigepaytool.entity.Config
import com.sdk.fenigepaytool.entity.PaytoolResultCallback
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender
import org.json.JSONArray
import java.util.UUID

class MainActivity: GPayActivity(), PaytoolResultCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        with(binding) {
            ArrayAdapter.createFromResource(
                this@MainActivity,
                R.array.currencies,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                currencySpinner.adapter = adapter
            }
            ArrayAdapter.createFromResource(
                this@MainActivity,
                R.array.config,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                configSpinner.adapter = adapter
            }
            transactionIdInput.setText(UUID.randomUUID().toString())
            apiKeyInput.setText("a0185ee7-a197-4e3e-a830-a5d6992fba2f ")
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            val paymentMethods: JSONArray = JSONArray().put(PaymentsUtil.cardPaymentMethod())
            gpayBtn.apply {
                initialize(
                    ButtonOptions.newBuilder()
                        .setButtonTheme(ButtonConstants.ButtonTheme.LIGHT)
                        .setButtonType(ButtonConstants.ButtonType.CHECKOUT)
                        .setAllowedPaymentMethods(paymentMethods.toString())
                        .setCornerRadius(100)
                        .build()
                )
                setOnClickListener {
                    val amount = amountInput.text.toString().toInt()
                    val currency = currencySpinner.selectedItem.toString()
                    possiblyShowGooglePayButton(amount, currency)
                }
            }
            createPaymentBtn.setOnClickListener {
                val apiKey = apiKeyInput.text.toString()
                val transactionId = transactionIdInput.text.toString()
                val description = descriptionInput.text.toString()
                val amount = amountInput.text.toString().toInt()
                val autoClear = autocleared.isChecked
                val currency = currencySpinner.selectedItem.toString()
                val recurring = recurring.isChecked
                val config =
                    if (configSpinner.selectedItem.toString() == Config.PROD.name) Config.PROD else Config.DEBUG

                FenigePaytool.initPayment(
                    activity = this@MainActivity,
                    apiKey = apiKey,
                    currencyCode = currency,
                    amount = amount,
                    description = description,
                    merchantUrl = "https://paytool-dev.fenige.pl/demo/",
                    orderNumber = "123",
                    formLanguage = "en",
                    redirectUrl = RedirectUrl("https://testok.com", "https://testfaill.com"),
                    sender = Sender(
                        firstName = "Test", lastName = "Test",
                        address = Address("PL", "Krakow", "03-355", "vylitsya", "15")
                    ),
                    transactionId = transactionId,
                    autoClear = autoClear,
                    config = config,
                    isRecurring = recurring,
                    paytoolResultCallback = this@MainActivity
                )
            }
        }
    }

    override fun onResult(token: String) {
        with(binding) {
            val apiKey = apiKeyInput.text.toString()
            val transactionId = transactionIdInput.text.toString()
            val description = descriptionInput.text.toString()
            val amount = amountInput.text.toString().toInt()
            val autoClear = autocleared.isChecked
            val recurring = recurring.isChecked
            val currency = currencySpinner.selectedItem.toString()
            val config =
                if (configSpinner.selectedItem.toString() == Config.PROD.name) Config.PROD else Config.DEBUG

            FenigePaytool.gpayPayment(
                activity = this@MainActivity,
                apiKey = apiKey,
                currencyCode = currency,
                amount = amount,
                description = description,
                merchantUrl = "https://paytool-dev.fenige.pl/demo/",
                orderNumber = "123",
                formLanguage = "en",
                redirectUrl = RedirectUrl("https://testok.com", "https://testfaill.com"),
                sender = Sender(
                    firstName = "Test", lastName = "Test",
                    address = Address("PL", "Krakow", "03-355", "vylitsya", "15")
                ),
                transactionId = transactionId,
                autoClear = autoClear,
                config = config,
                paytoolResultCallback = this@MainActivity,
                token = token,
                isRecurring = recurring,
                email = "test@gmail.com"
            )
        }
    }

    override fun onFinishCallback(transactionId: String) {
        binding.transactionId.apply {
            text = "TransactionId: $transactionId"
            visibility = View.VISIBLE
            setOnClickListener {
                val clipboard: ClipboardManager =
                    getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("transactionId", transactionId)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this@MainActivity, "Copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }
    }
}