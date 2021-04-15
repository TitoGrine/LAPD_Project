package org.jetbrains.middleware

import org.eclipse.lsp4j.jsonrpc.Launcher
import org.jetbrains.middleware.builder.server.MiddlewareServer
import org.jetbrains.middleware.builder.server.requests.JsonRpcRequestBuilder
import org.jetbrains.middleware.builder.strategies.JsonRpcStrategy
import java.util.concurrent.CompletableFuture

class ChatClientExample : ChatClient {
    override fun didPostMessage(message: ChatServer.UserMessage?) {
        message?.let {
            println("${it.user}: ${it.content}")
        }
    }
}


fun main() {
    MiddlewareServer.Builder<List<JsonRpcStrategy.JsonRpcObject>, CompletableFuture<*>>()
        .portToServe(8000)
        .serverUrl("localhost:3000")
        .setStrategy(JsonRpcStrategy("localhost", 3000, ChatServer::class.java, ChatClientExample()))
        .addRequestBuilder(JsonRpcRequestBuilder(ChatServer::class.java))
        .build()
        .start()
    println("Finish listening")
}