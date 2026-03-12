package com.uzuu.learn4_roomdb_retrofitapi_basic.core.result

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()

    // truyen 2 tham so, cho phep nem exception
    data class Error(val message: String, val throwable : Throwable ?= null): ApiResult<Nothing>()

    // 1 tham so (chi in ra loi)
    //data class Error(val message: String) : ApiResult<Nothing>()
}