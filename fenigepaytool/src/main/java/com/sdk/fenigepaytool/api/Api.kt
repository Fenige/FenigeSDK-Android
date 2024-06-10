package com.sdk.fenigepaytool.api

import com.sdk.fenigepaytool.api.request.PaytoolRequest
import com.sdk.fenigepaytool.api.response.TransactionIdResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface Api {

    companion object {
        private const val API_KEY_HEADER = "Api-Key"
    }

    @POST("transactions/pre-initialization")
    suspend fun getTransactionId(@Header(API_KEY_HEADER) header: String, @Body request: PaytoolRequest): TransactionIdResponse

}