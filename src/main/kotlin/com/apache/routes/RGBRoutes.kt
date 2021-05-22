package com.apache.routes

import com.apache.converters.GenericColor_2_Color
import com.generic.controllers.buildRGBColor
import com.generic.controllers.getRandomRGBColor
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rgbRouting() {
    route("/rgb") {
        get("/random") {
            call.respond(GenericColor_2_Color(buildRGBColor(getRandomRGBColor())))
        }
        /*post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()

            printCall("/rgb/convert")

            if (!conversionRequest.color.colorDef.hasRgbMode())
                call.respondText("Color Mode must be RGB", status = HttpStatusCode.BadRequest)

            val genericConversionRequest = ConversionRequest_2_GenericConversionRequest(conversionRequest)

            if (genericConversionRequest == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else {
                val conversionResponse = GenericConversionResponse_2_ConversionResponse(
                    convertColor(
                        genericConversionRequest
                    )
                ) ?: call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)

                call.respond(conversionResponse)
            }
        }
        post("/palette") {
            val paletteRequest = call.receive<Colors.ColorPaletteRequest>()

            printCall("/rgb/palette")

            if (!paletteRequest.color.colorDef.hasRgbMode())
                call.respondText("Color Mode must be RGB", status = HttpStatusCode.BadRequest)

            val genericPaletteRequest = PaletteRequest_2_GenericPaletteRequest(paletteRequest)

            if (genericPaletteRequest == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else {
                val paletteResponse = GenericPaletteResponse_2_PaletteResponse(
                    generatePalette(
                        genericPaletteRequest
                    )
                ) ?: call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)

                call.respond(paletteResponse)
            }
        }*/
    }
}

fun Application.registerRGBRoutes() {
    routing {
        rgbRouting()
    }
}
