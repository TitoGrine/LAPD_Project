package org.jetbrains.middleware.builder

import arrow.core.Either

/** To then decode the values using Jackson
val mapper = jacksonObjectMapper()
val typeFactory = mapper.typeFactory
val mapType = typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
 **/


data class RequestDetails<T, K>(val url: String, val requestData: RequestData<T, K>)

data class RequestData<T, K>(val params: RequestParams<T>, val response: RequestResponse<T, K>)

interface RequestParams<T>{
    fun encode(body: String): T
}

interface RequestResponse<T, K>{
    suspend fun decode(response: K) : Either<String, String>
}
