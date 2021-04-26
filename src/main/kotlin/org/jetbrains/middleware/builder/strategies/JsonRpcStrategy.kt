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
import java.net.Socket
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

val gson = Gson()

class JsonRpcStrategy<T>(private val host: String, port: Int, interfaceClass: Class<T>, client: Any) :
    APITypeStrategy<List<*>, CompletableFuture<*>> {

    private val launcher: Launcher<T> by lazy {
        // connect to the server
        val socket = Socket(host, port)
        // open a JSON-RPC connection for the opened socket
        val launcher = Launcher.Builder<T>()
            .setRemoteInterface(interfaceClass)
            .setExecutorService(Executors.newSingleThreadExecutor())
            .setInput(socket.getInputStream())
            .setOutput(socket.getOutputStream())
            .setLocalService(client)
            .create()
        /*
             * Start listening for incoming messages.
             * When the JSON-RPC connection is closed
             * disconnect the remote client from the chat server.
             */
        Thread {
            launcher.startListening().get()
            socket.close()
        }.start()

        launcher
    }

    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<List<*>, CompletableFuture<*>>,
        parameters: String
    ): Either<String, String> {
        val encodedParams = requestData.params.encode(parameters)
        val response = launcher.remoteEndpoint.request(url, if (encodedParams.size == 1) encodedParams[0] else encodedParams)

        return requestData.response.decode(response)
    }


    data class JsonRpcParams(private val parameterTypes: List<Type>) : RequestParams<List<*>> {
        override fun encode(body: String): List<Any> = if (parameterTypes.isEmpty()) {
            emptyList()
        } else {
            val element = JsonParser.parseString(body)
            val obj = element.asJsonArray

            obj.mapIndexed { i, el -> gson.fromJson(el, parameterTypes[i]) }
        }

    }

    data class JsonRpcResponse(val returnType: Type) : RequestResponse<List<*>, CompletableFuture<*>> {
        override suspend fun decode(response: CompletableFuture<*>): Either<String, String> {
            val encodedResponse = gson.toJson(response.await(), returnType)
            return Either.Right(encodedResponse)
        }
    }
}