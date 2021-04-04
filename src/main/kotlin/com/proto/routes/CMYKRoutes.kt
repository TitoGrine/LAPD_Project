package com.proto.routes

import com.proto.controllers.getRandomCMYKColor
import com.proto.models.Colors.Color
import com.proto.models.Colors.Color.Mode
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.cmykRouting() {
    route("/cmyk") {
        get("/random") {
            val color = Color.newBuilder()
                .setColorDef(Mode.newBuilder()
                    .setCmykMode(getRandomCMYKColor()))
                .build()

            call.respond(color)
        }
    }
}

fun Application.registerCMYKRoutes() {
    routing {
        cmykRouting()
    }
}