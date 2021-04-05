package com.proto.routes

import com.proto.controllers.buildHSVColor
import com.proto.controllers.convertColor
import com.proto.controllers.getRandomHSVColor
import com.proto.models.Colors
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hsvRouting() {
    route("/hsv") {
        get("/random") {
            call.respond(buildHSVColor(getRandomHSVColor()))
        }
        post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()
            val conversionResponse = convertColor(conversionRequest)

            if(conversionResponse == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else{
                call.respond(conversionResponse)
            }
        }
    }
}

fun Application.registerHSVRoutes() {
    routing {
        hsvRouting()
    }
}