package com.proto

import com.proto.routes.registerCMYKRoutes
import com.proto.routes.registerHSVRoutes
import com.proto.routes.registerHexRoutes
import com.proto.routes.registerRGBRoutes
import com.proto.serializers.ColorSerializer
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlinx.serialization.ExperimentalSerializationApi
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        register(ContentType.Application.ProtoBuf, ColorSerializer)
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }

    registerRGBRoutes()
    registerCMYKRoutes()
    registerHSVRoutes()
    registerHexRoutes()
}