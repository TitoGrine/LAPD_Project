package com.proto.controllers

import com.proto.models.Colors
import kotlin.math.max

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

    return when(toModel){
        Colors.ColorMode.HEX_MODE -> null
        Colors.ColorMode.RGB_MODE -> buildRGBColor(red, green, blue)
        Colors.ColorMode.CMYK_MODE -> {
            var Rc: Float = (red / 255.0).toFloat()
            var Gc: Float = (green / 255.0).toFloat()
            var Bc: Float = (blue / 255.0).toFloat()

            var Kf = max(max(Rc, Gc), Bc)

            var cyan = (1 - Rc - Kf) / Kf
            var magenta = (1 - Gc - Kf) / Kf
            var yellow = (1 - Bc - Kf) / Kf

            return buildCMYKColor(cyan, magenta, yellow, 1 - Kf)
        }
        Colors.ColorMode.HSV_MODE -> null
    }
}

fun convertCMYKColor(color: Colors.CMYK, toModel: Colors.ColorMode): Colors.Color? {
    return null
}

fun convertHSVColor(color: Colors.HSV, toModel: Colors.ColorMode): Colors.Color? {
    return null
}