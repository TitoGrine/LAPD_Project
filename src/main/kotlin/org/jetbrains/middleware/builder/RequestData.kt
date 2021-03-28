package org.jetbrains.middleware.builder

/** To then decode the values using Jackson
    val mapper = jacksonObjectMapper()
    val typeFactory = mapper.typeFactory
    val mapType = typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
**/


data class RequestDetails<T, K>(val url: String, val requestData: RequestData<T, K>)

data class RequestData<T, K>(val params: RequestParams<T, K>, val response: RequestResponse<T, K>)

interface RequestParams<T, K>{
    val data: K
    fun encode(body: String): T
}

interface RequestResponse<T, K>{
    val data: K
    fun decode(body: String) : T
}
