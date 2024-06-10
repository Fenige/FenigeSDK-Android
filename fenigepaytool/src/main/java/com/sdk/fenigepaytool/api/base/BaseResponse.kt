package com.sdk.fenigepaytool.api.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class BaseResponse<T>(
    @Json(name = "errorType") var errorType: String?,
    @Json(name = "transactionId") var data: T?
)