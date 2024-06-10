package com.sdk.fenigepaytool.entity
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RedirectUrl (
    @Json(name = "successUrl") val successUrl: String,
    @Json(name = "failureUrl") val failureUrl: String
): Parcelable