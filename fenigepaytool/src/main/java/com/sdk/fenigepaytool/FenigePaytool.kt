package com.sdk.fenigepaytool

import androidx.appcompat.app.AppCompatActivity
import com.sdk.fenigepaytool.entity.PaytoolResultCallback
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender
import com.sdk.fenigepaytool.ui.PaytoolActivity

public object FenigePaytool {
    public fun initPayment(
        activity: AppCompatActivity, apiKey: String, currencyCode: String,
        amount: Int, description: String,
        merchantUrl: String, orderNumber: String,
        formLanguage: String, redirectUrl: RedirectUrl,
        sender: Sender, transactionId: String,
        paytoolResultCallback: PaytoolResultCallback
    ) {
        PaytoolActivity.launch(
            activity,
            apiKey,
            currencyCode,
            amount,
            description,
            merchantUrl,
            orderNumber,
            formLanguage,
            redirectUrl,
            sender,
            transactionId,
            paytoolResultCallback
        )
    }
}