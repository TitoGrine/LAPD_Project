package org.jetbrains.middleware.builder

/** To then decode the values using Jackson
val mapper = jacksonObjectMapper()
val typeFactory = mapper.typeFactory
val mapType = typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
 **/


data class RequestDetails<T>(val url: String, val requestData: RequestData<T>)

data class RequestData<T>(val params: RequestParams<T>, val response: RequestResponse<T>)

interface RequestParams<T>{
    fun encode(body: String): T
}

interface RequestResponse<T>{
    fun decode(body: String) : T
}
