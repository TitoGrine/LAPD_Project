package org.jetbrains.middleware.builder

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.middleware.builder.strategies.APITypeStrategy
import java.net.URI

class MiddlewareServer<T> private constructor(
    val portToServe: Int,
    val serverUrl: URI,
    val wait: Boolean,
    val requests: Map<String, RequestData<T>>,
    val strategy: APITypeStrategy<T>
) {
    data class Builder<T>(
        var portToServe: Int = 80,
        var serverUrl: URI? = null,
        var wait: Boolean = true,
        val requests: MutableMap<String, RequestData<T>> = mutableMapOf(),
        var strategy: APITypeStrategy<T>? = null
    ) {
        fun portToServe(portToServe: Int): Builder<T> = apply { this.portToServe = portToServe }
        fun serverUrl(serverUrl: String): Builder<T> = apply { this.serverUrl = URI.create(serverUrl) }
        fun setStrategy(strategy: APITypeStrategy<T>): Builder<T> = apply { this.strategy = strategy }
        fun addRequest(requestDetails: RequestDetails<T>): Builder<T> =
            apply { this.requests[requestDetails.url] = requestDetails.requestData }

        fun addRequests(requestsDetails: List<RequestDetails<T>>): Builder<T> =
            apply { requestsDetails.forEach { requestDetails -> this.addRequest(requestDetails) } }

        fun wait(wait: Boolean): Builder<T> = apply { this.wait = wait }
        fun build(): MiddlewareServer<T> =
            if(serverUrl != null && strategy != null) {
                MiddlewareServer(
                    portToServe,
                    serverUrl!!,
                    wait,
                    requests.toMap(),
                    strategy!!
                )
            } else {
                throw Exception("No server url provided")
            }
    }

    fun start(): NettyApplicationEngine {
        return embeddedServer(Netty, port = portToServe) {
            routing {
                requests.forEach { (url, requestData) ->
                    post(url) {
                        strategy.sendRequest(url, requestData);
                        context.respondText("Got request in ${call.request.uri}")
                    }
                }
            }
        }.start(wait = wait)
    }
}
