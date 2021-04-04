package com.proto.routes

import com.proto.controllers.getRandomHexColor
import com.proto.models.Colors.Color
import com.proto.models.Colors.Color.Mode
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hexRouting() {
    route("/hex") {
        get("/random") {
            val color = Color.newBuilder()
                .setColorDef(Mode.newBuilder()
                    .setHexMode(getRandomHexColor()))
                .build()

            call.respond(color)
        }
    }
}

fun Application.registerHexRoutes() {
    routing {
        hexRouting()
    }
}