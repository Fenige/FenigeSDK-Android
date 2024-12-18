package com.sdk.fenigepaytool

import androidx.appcompat.app.AppCompatActivity
import com.sdk.fenigepaytool.entity.Config
import com.sdk.fenigepaytool.entity.PaytoolResultCallback
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender
import com.sdk.fenigepaytool.ui.PaytoolActivity

object FenigePaytool {
    fun initPayment(
        activity: AppCompatActivity, apiKey: String, currencyCode: String,
        amount: Int, description: String,
        merchantUrl: String, orderNumber: String,
        formLanguage: String, redirectUrl: RedirectUrl,
        sender: Sender, transactionId: String,
        autoClear: Boolean, isRecurring: Boolean, config: Config,
        paytoolResultCallback: PaytoolResultCallback
    ) {
        PaytoolActivity.launch(
            activity = activity,
            apiKey = apiKey,
            currencyCode = currencyCode,
            amount = amount,
            description = description,
            merchantUrl = merchantUrl,
            orderNumber = orderNumber,
            formLanguage = formLanguage,
            redirectUrl = redirectUrl,
            sender = sender,
            transactionId = transactionId,
            autoClear = autoClear,
            isRecurring = isRecurring,
            config = config,
            paytoolResultCallback = paytoolResultCallback
        )
    }

    fun gpayPayment(
        activity: AppCompatActivity, apiKey: String,
        token: String, email: String, currencyCode: String,
        amount: Int, description: String,
        merchantUrl: String, orderNumber: String,
        formLanguage: String, redirectUrl: RedirectUrl,
        sender: Sender, transactionId: String,
        autoClear: Boolean,  isRecurring: Boolean, config: Config,
        paytoolResultCallback: PaytoolResultCallback
    ) {
        PaytoolActivity.launchWithGpay(
            activity = activity,
            apiKey = apiKey,
            token = token,
            email = email,
            currencyCode = currencyCode,
            amount = amount,
            description = description,
            merchantUrl = merchantUrl,
            orderNumber = orderNumber,
            formLanguage = formLanguage,
            redirectUrl = redirectUrl,
            sender = sender,
            transactionId = transactionId,
            autoClear = autoClear,
            isRecurring = isRecurring,
            config = config,
            paytoolResultCallback = paytoolResultCallback
        )
    }
}