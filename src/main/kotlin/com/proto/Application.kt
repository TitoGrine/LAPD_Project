package com.proto

import com.proto.routes.registerMessageRoutes
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import kotlinx.serialization.protobuf.ProtoBuf

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlinx.serialization.ExperimentalSerializationApi
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        serialization(ContentType.Application.ProtoBuf, ProtoBuf.Default)
    }
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }

    registerMessageRoutes()
}