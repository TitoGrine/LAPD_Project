package com.proto.routes

import com.proto.controllers.buildHEXColor
import com.proto.controllers.getRandomHexColor
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hexRouting() {
    route("/hex") {
        get("/random") {
            call.respond(buildHEXColor(getRandomHexColor()))
        }
    }
}

fun Application.registerHexRoutes() {
    routing {
        hexRouting()
    }
}