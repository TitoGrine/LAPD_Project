package com.proto.controllers

import com.proto.models.Colors
import kotlin.math.max

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

fun convertHexColor(color: Colors.HEX, toModel: Colors.ColorMode): Colors.Color?  {
    return null
}

fun convertRGBColor(color: Colors.RGB, toModel: Colors.ColorMode): Colors.Color? {
    val red = color.red
    val green = color.green
    val blue = color.blue

    return when(toModel){
        Colors.ColorMode.HEX_MODE -> null
        Colors.ColorMode.RGB_MODE -> buildRGBColor(red, green, blue)
        Colors.ColorMode.CMYK_MODE -> {
            val Rc: Float = (red / 255.0).toFloat()
            val Gc: Float = (green / 255.0).toFloat()
            val Bc: Float = (blue / 255.0).toFloat()

            val K = 1 - max(max(Rc, Gc), Bc)

            val cyan = (1 - Rc - K) / (1 - K)
            val magenta = (1 - Gc - K) / (1 - K)
            val yellow = (1 - Bc - K) / (1 - K)

            return buildCMYKColor(cyan, magenta, yellow, K)
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