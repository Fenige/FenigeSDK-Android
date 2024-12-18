package com.sdk.fenigepaytool.api.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
internal data class GpayRequest (
    @Json(name = "transactionId") val transactionId: String,
    @Json(name = "sender") val sender: Sender,
    @Json(name = "token") val token: Token,
    @Json(name = "language") val language: Language,
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
internal data class Token (
    @Json(name = "signature") val signature: String,
    @Json(name = "intermediateSigningKey") val intermediateSigningKey: IntermediateSigningKey,
    @Json(name = "protocolVersion") val protocolVersion: String,
    @Json(name = "signedMessage") val signedMessage: String,
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
internal data class IntermediateSigningKey (
    @Json(name = "signedKey") val signedKey: String,
    @Json(name = "signatures") val signatures: List<String>,
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
internal data class Sender (
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "email") val email: String,
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
internal data class Language (
    @Json(name = "language") val language: String,
): Parcelable