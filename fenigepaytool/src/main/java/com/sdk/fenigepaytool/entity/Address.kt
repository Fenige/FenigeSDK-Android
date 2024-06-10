package com.sdk.fenigepaytool.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Address (
    @Json(name = "countryCode") val countryCode: String,
    @Json(name = "city") val city: String,
    @Json(name = "postalCode") val postalCode: String,
    @Json(name = "street") val street: String,
    @Json(name = "houseNumber") val houseNumber: String
): Parcelable