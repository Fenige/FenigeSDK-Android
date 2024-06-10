package com.sdk.fenigepaytool.ui

import androidx.lifecycle.ViewModel
import com.sdk.fenigepaytool.api.helpers.NetworkResult
import com.sdk.fenigepaytool.api.request.PaytoolRequest
import com.sdk.fenigepaytool.repository.PaytoolRepository
import com.sdk.fenigepaytool.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class PaytoolViewModel(private val paytoolRepository: PaytoolRepository): ViewModel() {

    internal val transactionId = SingleLiveEvent<String>()
    internal val error = SingleLiveEvent<String>()

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private var scope = CoroutineScope(coroutineContext)

    internal fun getTransactionId(apiKey: String, paytoolRequest: PaytoolRequest) = scope.launch {
        paytoolRepository.getTransactionId(apiKey, paytoolRequest).apply {
            when (this) {
                is NetworkResult.Success -> {
                    this.data.transactionId?.let {
                        transactionId.postValue(it)
                    }
                    this.data.errorType?.let {
                        error.postValue(it)
                    }
                }
                is NetworkResult.Error -> {
                    error.postValue("Network Error")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}