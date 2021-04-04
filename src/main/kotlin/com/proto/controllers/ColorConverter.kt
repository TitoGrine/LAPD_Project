package com.proto.controllers

import com.proto.models.Colors

fun convertColor(request: Colors.ColorConversionRequest): Colors.ColorConversionResponse? {
    var originalColor = request.color
    var requestedType = request.colorMode

    var convertedColor = when {
        originalColor.colorDef.hasHexMode() -> convertHexColor(originalColor.colorDef as Colors.HEX, requestedType)
        originalColor.colorDef.hasRgbMode() -> convertRGBColor(originalColor.colorDef as Colors.RGB, requestedType)
        originalColor.colorDef.hasCmykMode() -> convertCMYKColor(originalColor.colorDef as Colors.CMYK, requestedType)
        originalColor.colorDef.hasHsvMode() -> convertHSVColor(originalColor.colorDef as Colors.HSV, requestedType)
        else -> null
    }

    return Colors.ColorConversionResponse
        .newBuilder()
        .setColor(convertedColor)
        .setColorMode(requestedType)
        .build()
}

fun convertHexColor(color: Colors.HEX, toModel: Colors.ColorMode): Colors.Color?  {
    return null
}

fun convertRGBColor(color: Colors.RGB, toModel: Colors.ColorMode): Colors.Color? {
    var red = color.red
    var green = color.green
    var blue = color.blue

    when(toModel){
        Colors.ColorMode.HEX_MODE -> null
        Colors.ColorMode.RGB_MODE -> null
        Colors.ColorMode.CMYK_MODE -> null
        Colors.ColorMode.HSV_MODE -> null
    }

    return null
}

fun convertCMYKColor(color: Colors.CMYK, toModel: Colors.ColorMode): Colors.Color? {
    return null
}

fun convertHSVColor(color: Colors.HSV, toModel: Colors.ColorMode): Colors.Color? {
    return null
}