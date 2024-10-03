package com.sdk.fenigepaytool.api.request

import android.os.Parcelable
import com.sdk.fenigepaytool.entity.RedirectUrl
import com.sdk.fenigepaytool.entity.Sender
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
internal data class PaytoolRequest (
    @Json(name = "transactionId") val transactionId: String,
    @Json(name = "currencyCode") val currencyCode: String,
    @Json(name = "amount") val amount: Int,
    @Json(name = "description") val description: String,
    @Json(name = "formLanguage") val formLanguage: String,
    @Json(name = "redirectUrl") val redirectUrl: RedirectUrl,
    @Json(name = "sender") val sender: Sender,
    @Json(name = "merchantUrl") val merchantUrl: String,
    @Json(name = "orderNumber") val orderNumber: String,
    @Json(name = "autoClear") val autoClear: Boolean = false,
    @Json(name = "typeOfAuthorization") val typeOfAuthorization: String,
): Parcelable