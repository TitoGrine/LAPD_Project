package com.proto.routes

import com.proto.controllers.buildHSVColor
import com.proto.controllers.getRandomHSVColor
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hsvRouting() {
    route("/hsv") {
        get("/random") {
            call.respond(buildHSVColor(getRandomHSVColor()))
        }
    }
}

fun Application.registerHSVRoutes() {
    routing {
        hsvRouting()
    }
}