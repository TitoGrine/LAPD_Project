package org.jetbrains.middleware.builder

import com.google.gson.Gson

data class RequestDetails<T>(val url: String, val requestData: RequestData<T>)

data class RequestData<T>(val params: RequestParams<T>, val response: RequestResponse)

interface RequestParams<T>{
    fun encode(): T
}

interface RequestResponse{
    fun decode()
}
