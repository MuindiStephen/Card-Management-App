package com.muindi.stephen.co_opbankapp.domain.utils

sealed class Resource<out T> {

    data class Data<T>(val value: T) : Resource<T>()

    data class Error(
        val message: String? = null,
        val throwable: Throwable? = null,
    ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}