package com.proto.controllers

import com.proto.models.Colors

fun buildHEXColor(hexDef: Colors.HEX): Colors.Color {
    var webSafe = isWebSafe(hexDef)

    return Colors.Color
        .newBuilder()
        .setColorDef(
            Colors.Color.Mode
                .newBuilder()
                .setHexMode(
                    hexDef
                )
        )
        .setWebSafe(webSafe)
        .build()
}

fun buildHEXColor(code: String): Colors.Color {
    return buildHEXColor(buildHEX(code))
}

fun buildHEX(code: String): Colors.HEX {
    return Colors.HEX
        .newBuilder()
        .setCode(code)
        .build()
}

fun buildRGBColor(rgbDef: Colors.RGB): Colors.Color {
    var webSafe = isWebSafe(rgbDef)

    return Colors.Color
        .newBuilder()
        .setColorDef(
            Colors.Color.Mode
                .newBuilder()
                .setRgbMode(
                    rgbDef
                )
        )
        .setWebSafe(webSafe)
        .build()
}

fun buildRGBColor(red: Int, green: Int, blue: Int): Colors.Color {
    return buildRGBColor(buildRGB(red, green, blue))
}

fun buildRGB(red: Int, green: Int, blue: Int): Colors.RGB {
    return Colors.RGB
        .newBuilder()
        .setRed(red)
        .setGreen(green)
        .setBlue(blue)
        .build()
}

fun buildCMYKColor(cmykDef: Colors.CMYK): Colors.Color {
    var webSafe = isWebSafe(cmykDef)

    return Colors.Color
        .newBuilder()
        .setColorDef(
            Colors.Color.Mode
                .newBuilder()
                .setCmykMode(
                    cmykDef
                )
        )
        .setWebSafe(webSafe)
        .build()
}

fun buildCMYKColor(cyan: Float, magenta: Float, yellow: Float, key: Float): Colors.Color {
    return buildCMYKColor(buildCMYK(cyan, magenta, yellow, key))
}

fun buildCMYK(cyan: Float, magenta: Float, yellow: Float, key: Float): Colors.CMYK {
    return Colors.CMYK
        .newBuilder()
        .setCyan(cyan)
        .setMagenta(magenta)
        .setYellow(yellow)
        .setKey(key)
        .build()
}

fun buildHSVColor(hsvDef: Colors.HSV): Colors.Color {
    var webSafe = isWebSafe(hsvDef)

    return Colors.Color
        .newBuilder()
        .setColorDef(
            Colors.Color.Mode
                .newBuilder()
                .setHsvMode(
                    hsvDef
                )
        )
        .setWebSafe(webSafe)
        .build()
}

fun buildHSVColor(hue: Int, saturation: Float, value: Float): Colors.Color {
    return buildHSVColor(hue, saturation, value)
}

fun buildHSV(hue: Int, saturation: Float, value: Float): Colors.HSV {
    return Colors.HSV
        .newBuilder()
        .setHue(hue)
        .setSaturation(saturation)
        .setValue(value)
        .build()
}
