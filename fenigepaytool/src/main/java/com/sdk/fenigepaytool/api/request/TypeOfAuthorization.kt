package com.sdk.fenigepaytool.api.request

enum class TypeOfAuthorization {
    COF,
    PURCHASE;

    companion object {
        fun getType(isRecurring: Boolean) =
            if (isRecurring) COF
            else PURCHASE
    }
}