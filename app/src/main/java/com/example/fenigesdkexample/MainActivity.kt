package com.example.fenigesdkexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fenigeexample.databinding.ActivityMainBinding
import com.sdk.fenigepaytool.FenigePaytool
import com.sdk.fenigepaytool.entity.Address
import com.sdk.fenigepaytool.entity.PaytoolResultCallback
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender

class MainActivity: AppCompatActivity(), PaytoolResultCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FenigePaytool.initPayment(
            activity = this,
            apiKey = "a0185ee7-a197-4e3e-a830-a5d6992fba2f",
            currencyCode = "USD",
            amount = 1,
            description = "description",
            merchantUrl = "https://paytool-dev.fenige.pl/demo/",
            orderNumber = "123",
            formLanguage = "en",
            redirectUrl = RedirectUrl("https://testok.com", "https://testfaill.com"),
            sender = Sender(
                firstName = "Test", lastName = "Test",
                address = Address("PL", "Krakow", "03-355", "vylitsya", "15")
            ),
            transactionId = "f1748c0a-5218-4e09-9c78-acc66a152138",
            paytoolResultCallback = this
        )
    }

    override fun onFinishCallback(transactionId: String) {

    }
}