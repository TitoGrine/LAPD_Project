@file:JvmName("JsonRpcExample")
package org.jetbrains.middleware

import com.jrpc.shared.ColorClient
import com.jrpc.shared.ColorServer
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.jetbrains.middleware.builder.server.MiddlewareServer
import org.jetbrains.middleware.builder.server.requests.JsonRpcRequestBuilder
import org.jetbrains.middleware.builder.strategies.JsonRpcStrategy
import java.util.concurrent.CompletableFuture


class ClientExample : ColorClient


fun main() {
    MiddlewareServer.Builder<List<JsonRpcStrategy.JsonRpcObject>, CompletableFuture<*>>()
        .portToServe(8888)
        .serverUrl("localhost:3000")
        .setStrategy(JsonRpcStrategy("localhost", 3000, ColorServer::class.java, ClientExample()))
        .addRequestBuilder(JsonRpcRequestBuilder(ColorServer::class.java))
        .build()
        .start()
}