package com.sdk.fenigepaytool.repository

import com.sdk.fenigepaytool.api.Api
import com.sdk.fenigepaytool.api.helpers.NetworkResult
import com.sdk.fenigepaytool.api.helpers.safeApiResult
import com.sdk.fenigepaytool.api.request.PaytoolRequest
import com.sdk.fenigepaytool.api.response.TransactionIdResponse

internal class PaytoolRepository(private val api: Api) {

    internal suspend fun getTransactionId(
        apiKey: String,
        paytoolRequest: PaytoolRequest
    ): NetworkResult<TransactionIdResponse> = safeApiResult {
        api.getTransactionId(apiKey, paytoolRequest)
    }
}