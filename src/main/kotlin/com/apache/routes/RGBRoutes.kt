package com.apache.routes

import com.apache.converters.*
import com.apache.models.Colors
import com.apache.printCall
import com.generic.controllers.buildRGBColor
import com.generic.controllers.convertColor
import com.generic.controllers.generatePalette
import com.generic.controllers.getRandomRGBColor
import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.io.AvroBinaryOutputStream
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.io.OutputStream

fun Route.rgbRouting() {
    route("/rgb") {
        get("/random") {
            printCall("/rgb/random")

            call.respond(GenericColor_2_Color(buildRGBColor(getRandomRGBColor())))
        }
        post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()

            printCall("/rgb/convert")

            if (!conversionRequest.color.colorDef.hasRgbMode())
                call.respondText("Color Mode must be RGB", status = HttpStatusCode.BadRequest)

            val genericConversionRequest =
                ConversionRequest_2_GenericConversionRequest(conversionRequest)

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
        }
    }
}

fun Application.registerRGBRoutes() {
    routing {
        rgbRouting()
    }
}
