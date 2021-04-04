package com.proto.routes

import com.proto.controllers.buildRGBColor
import com.proto.controllers.getRandomRGBColor
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rgbRouting() {
    route("/rgb") {
        get("/random") {
            call.respond(buildRGBColor(getRandomRGBColor()))
        }
    }
}

fun Application.registerRGBRoutes() {
    routing {
        rgbRouting()
    }
}