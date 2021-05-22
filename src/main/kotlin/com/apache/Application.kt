@file:JvmName("Application")
package com.apache

import com.apache.routes.registerCMYKRoutes
import com.apache.routes.registerHSVRoutes
import com.apache.routes.registerHexRoutes
import com.apache.routes.registerRGBRoutes
import com.github.avrokotlin.avro4k.Avro
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlinx.serialization.ExperimentalSerializationApi
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        serialization(ContentType.Application.Any, Avro.default)
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }

    registerHexRoutes()
    registerRGBRoutes()
    registerCMYKRoutes()
    registerHSVRoutes()
}
