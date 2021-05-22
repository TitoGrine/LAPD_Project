@file:JvmName("ApacheAvroApplication")
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
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit { start(args[0].toInt()) }

fun start(port: Int): NettyApplicationEngine {
    return embeddedServer(Netty, port) {
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
    }.start(wait = true)
}
