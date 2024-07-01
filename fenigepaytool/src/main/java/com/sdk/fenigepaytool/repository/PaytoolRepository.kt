package com.sdk.fenigepaytool.repository

import com.sdk.fenigepaytool.api.Api
import com.sdk.fenigepaytool.api.helpers.NetworkResult
import com.sdk.fenigepaytool.api.helpers.safeApiResult
import com.sdk.fenigepaytool.api.request.PaytoolRequest
import com.sdk.fenigepaytool.api.response.TransactionIdResponse
import com.sdk.fenigepaytool.entity.Config

internal class PaytoolRepository(private val api: Api) {

    internal suspend fun getTransactionId(
        apiKey: String,
        paytoolRequest: PaytoolRequest
    ): NetworkResult<TransactionIdResponse> =
        if (Api.config == Config.PROD)
            getTransactionIdProd(apiKey, paytoolRequest)
        else
            getTransactionIdDev(apiKey, paytoolRequest)

    private suspend fun getTransactionIdDev(
        apiKey: String,
        paytoolRequest: PaytoolRequest
    ): NetworkResult<TransactionIdResponse> = safeApiResult {
        api.getTransactionIdDev(apiKey, paytoolRequest)
    }

    private suspend fun getTransactionIdProd(
        apiKey: String,
        paytoolRequest: PaytoolRequest
    ): NetworkResult<TransactionIdResponse> = safeApiResult {
        api.getTransactionIdProd(apiKey, paytoolRequest)
    }
}