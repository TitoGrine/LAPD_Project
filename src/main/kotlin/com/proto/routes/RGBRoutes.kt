package com.proto.routes

import com.proto.controllers.buildRGBColor
import com.proto.controllers.convertColor
import com.proto.controllers.convertRGBColor
import com.proto.controllers.getRandomRGBColor
import com.proto.models.Colors
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rgbRouting() {
    route("/rgb") {
        get("/random") {
            call.respond(buildRGBColor(getRandomRGBColor()))
        }
        post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()
            print("""Converted color = ${conversionRequest.toString()}""")

            val conversionResponse = convertColor(conversionRequest)

            if(conversionResponse == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else{
                call.respond(conversionResponse)
            }
        }
    }
}

fun Application.registerRGBRoutes() {
    routing {
        rgbRouting()
    }
}