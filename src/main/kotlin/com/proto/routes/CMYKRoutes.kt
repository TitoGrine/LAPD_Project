package com.proto.routes

import com.proto.controllers.buildCMYKColor
import com.proto.controllers.getRandomCMYKColor
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.cmykRouting() {
    route("/cmyk") {
        get("/random") {
            call.respond(buildCMYKColor(getRandomCMYKColor()))
        }
    }
}

fun Application.registerCMYKRoutes() {
    routing {
        cmykRouting()
    }
}