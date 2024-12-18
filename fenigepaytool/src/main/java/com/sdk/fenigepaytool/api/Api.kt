package com.sdk.fenigepaytool.api

import com.sdk.fenigepaytool.api.request.GpayRequest
import com.sdk.fenigepaytool.api.request.PaytoolRequest
import com.sdk.fenigepaytool.api.response.TransactionIdResponse
import com.sdk.fenigepaytool.entity.Config
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface Api {

    companion object {
        private const val API_KEY_HEADER = "Api-Key"
        internal lateinit var config: Config

        private const val DEV_TRANSACTION_URL = "https://paytool-dev.fenige.pl/"
        private const val PROD_TRANSACTION_URL = "https://paytool.fenige.pl/"

        internal fun getTransactionUrl() =
            when (config) {
                Config.DEBUG -> DEV_TRANSACTION_URL
                Config.PROD -> PROD_TRANSACTION_URL
            }
    }

    @POST("https://paytool-api-dev.fenige.pl/transactions/pre-initialization")
    suspend fun getTransactionIdDev(@Header(API_KEY_HEADER) header: String, @Body request: PaytoolRequest): TransactionIdResponse

    @POST("https://paytool-api.fenige.pl/transactions/pre-initialization")
    suspend fun getTransactionIdProd(@Header(API_KEY_HEADER) header: String, @Body request: PaytoolRequest): TransactionIdResponse

    @POST("https://paytool-api-dev.fenige.pl/transactions/google-pay")
    suspend fun payByGooglePayDev(@Body request: GpayRequest): TransactionIdResponse

    @POST("https://paytool-api-dev.fenige.pl/transactions/google-pay")
    suspend fun payByGooglePayProd(@Body request: GpayRequest): TransactionIdResponse
}