package com.proto.routes

import com.proto.controllers.buildHSVColor
import com.proto.controllers.convertColor
import com.proto.controllers.generatePalette
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

            if (!conversionRequest.color.colorDef.hasHsvMode())
                call.respondText("Color Mode must be HSV", status = HttpStatusCode.BadRequest)

            val conversionResponse = convertColor(conversionRequest)

            if (conversionResponse == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else {
                call.respond(conversionResponse)
            }
        }
        post("/palette") {
            val paletteRequest = call.receive<Colors.ColorPaletteRequest>()

            if (!paletteRequest.color.colorDef.hasHsvMode())
                call.respondText("Color Mode must be HSV", status = HttpStatusCode.BadRequest)

            val paletteResponse = generatePalette(paletteRequest)

            if (paletteResponse == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else {
                call.respond(paletteResponse)
            }
        }
    }
}

fun Application.registerHSVRoutes() {
    routing {
        hsvRouting()
    }
}
