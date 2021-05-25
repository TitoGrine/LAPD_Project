@file:JvmName("JsonRpcExample")
package org.feup.lapd.middleware

import com.jrpc.shared.ColorClient
import com.jrpc.shared.ColorServer
import org.feup.lapd.middleware.builder.server.MiddlewareServer
import org.feup.lapd.middleware.builder.server.requests.JsonRpcRequestBuilder
import org.feup.lapd.middleware.builder.strategies.JsonRpcStrategy
import java.util.concurrent.CompletableFuture


class ClientExample : ColorClient


fun main() {
    MiddlewareServer.Builder<List<*>, CompletableFuture<*>>()
        .portToServe(8888)
        .serverUrl("localhost:3000")
        .setStrategy(JsonRpcStrategy("localhost", 3000, ColorServer::class.java, ClientExample()))
        .addRequestBuilder(JsonRpcRequestBuilder(ColorServer::class.java))
        .build()
        .start()
}