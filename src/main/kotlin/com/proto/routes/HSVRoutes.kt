package com.proto.routes

import com.generic.controllers.buildHSVColor
import com.generic.controllers.convertColor
import com.generic.controllers.generatePalette
import com.generic.controllers.getRandomHSVColor
import com.proto.converters.*
import com.proto.models.Colors
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hsvRouting() {
    route("/hsv") {
        get("/random") {
            call.respond(GenericColor_2_Color(buildHSVColor(getRandomHSVColor())))
        }
        post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()

            if (!conversionRequest.color.colorDef.hasHsvMode())
                call.respondText("Color Mode must be HSV", status = HttpStatusCode.BadRequest)

            val conversionResponse = GenericConversionResponse_2_ConversionResponse(
                convertColor(
                    ConversionRequest_2_GenericConversionRequest(conversionRequest)
                )
            )

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

            val paletteResponse = GenericPaletteResponse_2_PaletteResponse(
                generatePalette(
                    PaletteRequest_2_GenericPaletteRequest(paletteRequest)
                )
            )

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
