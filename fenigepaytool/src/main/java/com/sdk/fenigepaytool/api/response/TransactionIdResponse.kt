package com.sdk.fenigepaytool.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class TransactionIdResponse (
    @Json(name = "transactionId") val transactionId: String?,
    @Json(name = "errorType") var errorType: String?
)