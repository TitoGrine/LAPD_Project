package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import org.jetbrains.middleware.builder.RequestResponse
import java.lang.reflect.Type

val gson = Gson()

class JsonRpcStrategy(private val launcher: Launcher<*>) : APITypeStrategy<List<JsonRpcStrategy.JsonRpcObject>> {
    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<List<JsonRpcObject>>,
        parameters: String
    ): Either<String, String> {
        val encodedParams = requestData.params.encode(parameters)
        val response = launcher.remoteEndpoint.request(url, encodedParams).get()

        val returnType = (requestData.response as JsonRpcResponse).returnType
        val encodedResponse = gson.toJson(response, returnType)
        return Either.Right(encodedResponse)
    }

    sealed class JsonRpcObject : Any()

    data class JsonRpcParams(private val parameterTypes: List<Type>) : RequestParams<List<JsonRpcObject>> {
        override fun encode(body: String): List<JsonRpcObject> {
            val element = JsonParser.parseString(body)
            val obj = element.asJsonArray

            return obj.mapIndexed { i, el -> gson.fromJson(el, parameterTypes[i]) }
        }

    }

    data class JsonRpcResponse(val returnType: Type) : RequestResponse<List<JsonRpcObject>> {
        override fun decode(body: ByteArray): List<JsonRpcObject> {
            TODO("Not yet implemented")
        }
    }
}