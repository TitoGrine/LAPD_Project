package org.feup.lapd.middleware.builder.server.requests

import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints
import org.feup.lapd.middleware.builder.RequestData
import org.feup.lapd.middleware.builder.RequestDetails
import org.feup.lapd.middleware.builder.server.MiddlewareServer
import org.feup.lapd.middleware.builder.strategies.JsonRpcStrategy
import java.util.concurrent.CompletableFuture

class JsonRpcRequestBuilder(private val interfaceClass: Class<*>) : RequestBuilder<List<*>, CompletableFuture<*>> {
    override fun addRequests(builder: MiddlewareServer.Builder<List<*>, CompletableFuture<*>>) {
        val endpoints = ServiceEndpoints.getSupportedMethods(interfaceClass)
        val requestsDetails = endpoints.map { entry ->
            RequestDetails(
                entry.key,
                RequestData(
                    JsonRpcStrategy.JsonRpcParams(entry.value.parameterTypes.asList()),
                    JsonRpcStrategy.JsonRpcResponse(entry.value.returnType)
                ),

                )
        }
        builder.addRequests(requestsDetails)
    }
}