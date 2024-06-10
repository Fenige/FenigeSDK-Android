package com.sdk.fenigepaytool.entity

interface PaytoolResultCallback {
    fun onFinishCallback(transactionId: String)
}