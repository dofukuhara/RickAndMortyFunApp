package com.fukuhara.network

import com.fukuhara.common.exception.NoDataFound
import java.net.UnknownHostException

class NetworkErrorHandlerImpl : NetworkErrorHandler {
    override fun generateNoDataFoundCustomException(throwable: Throwable, content: String): NoDataFound {
        val defaultErrorMessage = "Due to a network issue, it was not possible to retrieve $content data.\nPlease try again later."

        val errorMessage = when (throwable) {
            is UnknownHostException -> defaultErrorMessage
            else -> throwable.message ?: defaultErrorMessage
        }

        return NoDataFound(errorMessage)
    }
}