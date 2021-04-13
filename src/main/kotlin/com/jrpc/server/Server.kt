package com.jrpc.server

import com.generic.controllers.*
import com.generic.models.GenericColors
import com.jrpc.shared.ColorServer
import java.util.concurrent.CompletableFuture

class Server : ColorServer {
    override fun getRandomHexColor(): CompletableFuture<GenericColors.Color> =
        CompletableFuture.completedFuture(buildHEXColor(com.generic.controllers.getRandomHexColor()))

    override fun getRandomRGBColor(): CompletableFuture<GenericColors.Color> =
        CompletableFuture.completedFuture(buildRGBColor(com.generic.controllers.getRandomRGBColor()))

    override fun getRandomCMYKColor(): CompletableFuture<GenericColors.Color> =
        CompletableFuture.completedFuture(buildCMYKColor(com.generic.controllers.getRandomCMYKColor()))

    override fun getRandomHSVColor(): CompletableFuture<GenericColors.Color> =
        CompletableFuture.completedFuture(buildHSVColor(com.generic.controllers.getRandomHSVColor()))

    override fun convertColor(request: GenericColors.ColorConversionRequest?): CompletableFuture<GenericColors.ColorConversionResponse> {
        if(request == null)
            return CompletableFuture.failedFuture(Throwable("Request can't be null"))

        val response = com.generic.controllers.convertColor(request) ?: return CompletableFuture.failedFuture(Throwable("Error converting color."))

        return CompletableFuture.completedFuture(response)
    }

    override fun generateColorPalette(request: GenericColors.ColorPaletteRequest?): CompletableFuture<GenericColors.ColorPaletteResponse> {
        if(request == null)
            return CompletableFuture.failedFuture(Throwable("Request can't be null"))

        val response = generatePalette(request) ?: return CompletableFuture.failedFuture(Throwable("Error converting color."))

        return CompletableFuture.completedFuture(response)
    }
}
