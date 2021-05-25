package org.jetbrains.middleware.builder.server

import arrow.core.Either
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestDetails
import org.jetbrains.middleware.builder.server.requests.RequestBuilder
import org.jetbrains.middleware.builder.strategies.APITypeStrategy
import java.net.URI

class MiddlewareServer<T, K> private constructor(
    val portToServe: Int,
    val serverUrl: URI,
    val wait: Boolean,
    val requests: Map<String, RequestData<T, K>>,
    val strategy: APITypeStrategy<T, K>
) {
    data class Builder<T, K>(
        var portToServe: Int = 80,
        var serverUrl: URI? = null,
        var wait: Boolean = true,
        val requests: MutableMap<String, RequestData<T, K>> = mutableMapOf(),
        var strategy: APITypeStrategy<T, K>? = null,
        var requestBuilder: RequestBuilder<T, K>? = null
    ) {
        fun portToServe(portToServe: Int): Builder<T, K> = apply { this.portToServe = portToServe }
        fun serverUrl(serverUrl: String): Builder<T, K> = apply { this.serverUrl = URI.create(serverUrl) }
        fun setStrategy(strategy: APITypeStrategy<T, K>): Builder<T, K> = apply { this.strategy = strategy }
        fun addRequest(requestDetails: RequestDetails<T, K>): Builder<T, K> =
            apply { this.requests[requestDetails.url] = requestDetails.requestData }

        fun addRequests(requestsDetails: List<RequestDetails<T, K>>): Builder<T, K> =
            apply { requestsDetails.forEach { requestDetails -> this.addRequest(requestDetails) } }

        fun addRequestBuilder(requestBuilder: RequestBuilder<T, K>) =
            apply { this.requestBuilder = requestBuilder }

        fun wait(wait: Boolean): Builder<T, K> = apply { this.wait = wait }
        fun build(): MiddlewareServer<T, K> {
            requestBuilder?.addRequests(this)
            return if (serverUrl != null && strategy != null) {
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
    }


    fun start(): NettyApplicationEngine {
        return embeddedServer(Netty, port = portToServe) {
            install(ContentNegotiation) {
                json()
            }
            routing {
                requests.forEach { (url, requestData) ->
                    post(url) {
                        val parameters = call.receive<String>()
                        when (val response = strategy.sendRequest(serverUrl.toString(), url, requestData, parameters)) {
                            is Either.Left -> context.respondText(response.value)
                            is Either.Right -> context.respondText(
                                response.value,
                                contentType = ContentType.Application.Json
                            )
                        }
                    }
                }
            }
        }.start(wait = wait)
    }
}