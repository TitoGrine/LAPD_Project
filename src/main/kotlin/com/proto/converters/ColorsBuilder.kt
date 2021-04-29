package com.proto.converters

import com.proto.models.Colors

fun buildHEXColor(hexDef: Colors.HEX, webSafe: Boolean = false): Colors.Color = Colors.Color
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

fun buildHEXColor(code: String, webSafe: Boolean = false): Colors.Color =
    com.grpc.converters.buildHEXColor(com.grpc.converters.buildHEX(code), webSafe)

fun buildHEX(code: String): Colors.HEX = Colors.HEX
    .newBuilder()
    .setCode(code)
    .build()

fun buildRGBColor(rgbDef: Colors.RGB, webSafe: Boolean = false): Colors.Color = Colors.Color
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

fun buildRGBColor(red: Int, green: Int, blue: Int, webSafe: Boolean = false): Colors.Color =
    com.grpc.converters.buildRGBColor(com.grpc.converters.buildRGB(red, green, blue), webSafe)

fun buildRGB(red: Int, green: Int, blue: Int): Colors.RGB = Colors.RGB
    .newBuilder()
    .setRed(red)
    .setGreen(green)
    .setBlue(blue)
    .build()

fun buildCMYKColor(cmykDef: Colors.CMYK, webSafe: Boolean = false): Colors.Color = Colors.Color
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

fun buildCMYKColor(cyan: Float, magenta: Float, yellow: Float, key: Float, webSafe: Boolean = false): Colors.Color =
    com.grpc.converters.buildCMYKColor(com.grpc.converters.buildCMYK(cyan, magenta, yellow, key), webSafe)

fun buildCMYK(cyan: Float, magenta: Float, yellow: Float, key: Float): Colors.CMYK = Colors.CMYK
    .newBuilder()
    .setCyan(cyan)
    .setMagenta(magenta)
    .setYellow(yellow)
    .setKey(key)
    .build()

fun buildHSVColor(hsvDef: Colors.HSV, webSafe: Boolean = false): Colors.Color = Colors.Color
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

fun buildHSVColor(hue: Int, saturation: Float, value: Float, webSafe: Boolean = false): Colors.Color =
    com.grpc.converters.buildHSVColor(
        com.grpc.converters.buildHSV(hue, saturation, value), webSafe
    )

fun buildHSV(hue: Int, saturation: Float, value: Float): Colors.HSV = Colors.HSV
    .newBuilder()
    .setHue(hue)
    .setSaturation(saturation)
    .setValue(value)
    .build()
