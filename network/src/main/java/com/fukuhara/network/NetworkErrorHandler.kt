package com.fukuhara.network

import com.fukuhara.common.exception.NoDataFound

interface NetworkErrorHandler {
    fun generateNoDataFoundCustomException(throwable: Throwable, content: String): NoDataFound
}