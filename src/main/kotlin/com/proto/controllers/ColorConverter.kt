package com.proto.controllers

import com.proto.models.Colors
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

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
        Colors.ColorMode.HEX_MODE -> {
            val code = """#${red.toString(16)}${green.toString(16)}${blue.toString(16)}"""

            return buildHEXColor(code.toUpperCase())
        }
        Colors.ColorMode.RGB_MODE -> {
            return buildRGBColor(red, green, blue)
        }
        Colors.ColorMode.CMYK_MODE -> {
            val Rc: Float = (red / 255.0).toFloat()
            val Gc: Float = (green / 255.0).toFloat()
            val Bc: Float = (blue / 255.0).toFloat()

            val K = 1 - max(max(Rc, Gc), Bc)

            if(1.0.compareTo(K) == 0)
                return buildCMYKColor(0.toFloat(),0.toFloat(),0.toFloat(), K)

            val cyan = (1 - Rc - K) / (1 - K)
            val magenta = (1 - Gc - K) / (1 - K)
            val yellow = (1 - Bc - K) / (1 - K)

            return buildCMYKColor(cyan, magenta, yellow, K)
        }
        Colors.ColorMode.HSV_MODE -> {
            val Rc: Float = (red / 255.0).toFloat()
            val Gc: Float = (green / 255.0).toFloat()
            val Bc: Float = (blue / 255.0).toFloat()

            val minVal = min(min(Rc, Gc), Bc)
            val maxVal = max(max(Rc, Gc), Bc)
            val delta = maxVal - minVal

            val value = maxVal

            if(1.0.compareTo(delta) == 0)
                return buildHSVColor(0, 0.toFloat(), value)

            val saturation = delta / maxVal

            val deltaR = (((maxVal - Rc) / 6) + (delta / 2)) / delta
            val deltaG = (((maxVal - Gc) / 6) + (delta / 2)) / delta
            val deltaB = (((maxVal - Bc) / 6) + (delta / 2)) / delta

            var hue: Float = when(maxVal){
                Rc -> deltaB - deltaG
                Gc -> (1.0 / 3.0) + deltaR - deltaB
                Bc -> (2.0 / 3.0) + deltaG - deltaR
                else -> 0
            }.toFloat()

            if(hue < 0) hue += 1
            if(hue > 1) hue -= 1

            hue *= 360

            return buildHSVColor(hue.roundToInt(), saturation, value)
        }
    }
}

fun convertCMYKColor(color: Colors.CMYK, toModel: Colors.ColorMode): Colors.Color? {
    return null
}

fun convertHSVColor(color: Colors.HSV, toModel: Colors.ColorMode): Colors.Color? {
    return null
}