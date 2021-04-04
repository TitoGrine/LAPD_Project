package com.proto.routes

import com.proto.controllers.getRandomHSVColor
import com.proto.models.Colors.Color
import com.proto.models.Colors.Color.Mode
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hsvRouting() {
    route("/hsv") {
        get("/random") {
            val color = Color.newBuilder()
                .setColorDef(Mode.newBuilder()
                    .setHsvMode(getRandomHSVColor()))
                .build()

            call.respond(color)
        }
    }
}

fun Application.registerHSVRoutes() {
    routing {
        hsvRouting()
    }
}