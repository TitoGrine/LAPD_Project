package com.proto.routes

import com.proto.controllers.getRandomRGBColor
import com.proto.models.Colors.Color
import com.proto.models.Colors.Color.Mode
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rgbRouting() {
    route("/rgb") {
        get("/random") {
            val color = Color.newBuilder()
                .setColorDef(Mode.newBuilder()
                    .setRgbMode(getRandomRGBColor()))
                .build()

            call.respond(color)
        }
    }
}

fun Application.registerRGBRoutes() {
    routing {
        rgbRouting()
    }
}