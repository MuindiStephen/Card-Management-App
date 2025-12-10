package com.muindi.stephen.co_opbankapp.domain.utils.constants

object Constants {


    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun getStringBaseUrl(): String
}