package com.grpc.shared

import com.generic.controllers.*
import com.grpc.converters.*
import com.grpc.models.*

class ColorsServiceImpl : ColorsServiceGrpcKt.ColorsServiceCoroutineImplBase() {
    override suspend fun getRandomHexColor(request: Void): Color =
        GenericColor_2_Color(buildHEXColor(getRandomHexColor()))

    override suspend fun getRandomRGBColor(request: Void): Color =
        GenericColor_2_Color(buildRGBColor(getRandomRGBColor()))

    override suspend fun getRandomCMYKColor(request: Void): Color =
        GenericColor_2_Color(buildCMYKColor(getRandomCMYKColor()))

    override suspend fun getRandomHSVColor(request: Void): Color =
        GenericColor_2_Color(buildHSVColor(getRandomHSVColor()))

    override suspend fun convertColor(request: ColorConversionRequest): ColorConversionResponse =
        GenericConversionResponse_2_ConversionResponse(convertColor(ConversionRequest_2_GenericConversionRequest(request)!!))!!

    override suspend fun generateColorPalette(request: ColorPaletteRequest): ColorPaletteResponse =
        GenericPaletteResponse_2_PaletteResponse(generatePalette(PaletteRequest_2_GenericPaletteRequest(request)!!))!!
}