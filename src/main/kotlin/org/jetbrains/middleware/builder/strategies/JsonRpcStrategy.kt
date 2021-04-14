package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.future.await
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import org.jetbrains.middleware.builder.RequestResponse
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture

val gson = Gson()

class JsonRpcStrategy(private val launcher: Launcher<*>) : APITypeStrategy<List<JsonRpcStrategy.JsonRpcObject>, CompletableFuture<*>> {
    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<List<JsonRpcObject>, CompletableFuture<*>>,
        parameters: String
    ): Either<String, String> {
        val encodedParams = requestData.params.encode(parameters)
        val response = launcher.remoteEndpoint.request(url, encodedParams)

        return requestData.response.decode(response)
    }

    sealed class JsonRpcObject : Any()

    data class JsonRpcParams(private val parameterTypes: List<Type>) : RequestParams<List<JsonRpcObject>> {
        override fun encode(body: String): List<JsonRpcObject> = if (parameterTypes.isEmpty()) {
            emptyList()
        } else {
            val element = JsonParser.parseString(body)
            val obj = element.asJsonArray

            obj.mapIndexed { i, el -> gson.fromJson(el, parameterTypes[i]) }
        }

    }

    data class JsonRpcResponse(val returnType: Type) : RequestResponse<List<JsonRpcObject>, CompletableFuture<*>> {
        override suspend fun decode(response: CompletableFuture<*>): Either<String, String> {
            val encodedResponse = gson.toJson(response.await(), returnType)
            return Either.Right(encodedResponse)
        }
    }
}