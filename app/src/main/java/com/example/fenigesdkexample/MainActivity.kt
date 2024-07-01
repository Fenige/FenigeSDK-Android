package com.example.fenigesdkexample

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fenigeexample.R
import com.example.fenigeexample.databinding.ActivityMainBinding
import com.sdk.fenigepaytool.FenigePaytool
import com.sdk.fenigepaytool.entity.Address
import com.sdk.fenigepaytool.entity.Config
import com.sdk.fenigepaytool.entity.PaytoolResultCallback
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender

class MainActivity: AppCompatActivity(), PaytoolResultCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
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

            createPaymentBtn.setOnClickListener {
                val apiKey = apiKeyInput.text.toString()
                val transactionId = transactionIdInput.text.toString()
                val description = descriptionInput.text.toString()
                val autoClear = autocleared.isChecked
                val currency = currencySpinner.selectedItem.toString()
                val config = if(configSpinner.selectedItem.toString() == Config.PROD.name) Config.PROD else Config.DEBUG

                FenigePaytool.initPayment(
                    activity = this@MainActivity,
                    apiKey = apiKey,
                    currencyCode = currency,
                    amount = 100,
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
                    paytoolResultCallback = this@MainActivity
                )
            }
        }
    }

    override fun onFinishCallback(transactionId: String) {
        binding.transactionId.apply {
            text = "TransactionId: ${transactionId}"
            visibility = View.VISIBLE
            setOnClickListener {
                Toast.makeText(this@MainActivity, " Copied to clipboard", Toast.LENGTH_LONG).show()
                val clipboard: ClipboardManager =
                    getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("transactionId", transactionId)
                clipboard.setPrimaryClip(clip)
            }
        }
    }
}