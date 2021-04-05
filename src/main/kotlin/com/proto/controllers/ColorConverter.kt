package com.proto.controllers

import com.proto.models.Colors

fun convertColor(request: Colors.ColorConversionRequest): Colors.ColorConversionResponse? {
    val originalColor = request.color
    val requestedType = request.colorMode

    val convertedColor = when {
        originalColor.colorDef.hasHexMode() -> convertHexColor(originalColor.colorDef.hexMode, requestedType)
        originalColor.colorDef.hasRgbMode() -> convertRGBColor(originalColor.colorDef.rgbMode, requestedType)
        originalColor.colorDef.hasCmykMode() -> convertCMYKColor(originalColor.colorDef.cmykMode, requestedType)
        originalColor.colorDef.hasHsvMode() -> convertHSVColor(originalColor.colorDef.hsvMode, requestedType)
        else -> null
    }

    return (
        if (convertedColor == null)
            null
        else
            Colors.ColorConversionResponse
                .newBuilder()
                .setColor(convertedColor)
                .setColorMode(requestedType)
                .build()
            )
}

fun convertHexColor(color: Colors.HEX, toModel: Colors.ColorMode): Colors.Color? = when(toModel){
    Colors.ColorMode.HEX_MODE -> buildHEXColor(HEX_2_HEX(color))
    Colors.ColorMode.RGB_MODE -> buildRGBColor(HEX_2_RGB(color))
    Colors.ColorMode.CMYK_MODE -> buildCMYKColor(HEX_2_CMYK(color))
    Colors.ColorMode.HSV_MODE -> buildHSVColor(HEX_2_HSV(color))
}

fun convertRGBColor(color: Colors.RGB, toModel: Colors.ColorMode): Colors.Color? = when(toModel){
    Colors.ColorMode.HEX_MODE -> buildHEXColor(RGB_2_HEX(color))
    Colors.ColorMode.RGB_MODE -> buildRGBColor(RGB_2_RGB(color))
    Colors.ColorMode.CMYK_MODE -> buildCMYKColor(RGB_2_CMYK(color))
    Colors.ColorMode.HSV_MODE -> buildHSVColor(RGB_2_HSV(color))
}

fun convertCMYKColor(color: Colors.CMYK, toModel: Colors.ColorMode): Colors.Color? = when(toModel){
    Colors.ColorMode.HEX_MODE -> buildHEXColor(CMYK_2_HEX(color))
    Colors.ColorMode.RGB_MODE -> buildRGBColor(CMYK_2_RGB(color))
    Colors.ColorMode.CMYK_MODE -> buildCMYKColor(CMYK_2_CMYK(color))
    Colors.ColorMode.HSV_MODE -> buildHSVColor(CMYK_2_HSV(color))
}

fun convertHSVColor(color: Colors.HSV, toModel: Colors.ColorMode): Colors.Color? = when(toModel){
    Colors.ColorMode.HEX_MODE -> buildHEXColor(HSV_2_HEX(color))
    Colors.ColorMode.RGB_MODE -> buildRGBColor(HSV_2_RGB(color))
    Colors.ColorMode.CMYK_MODE -> buildCMYKColor(HSV_2_CMYK(color))
    Colors.ColorMode.HSV_MODE -> buildHSVColor(HSV_2_HSV(color))
}