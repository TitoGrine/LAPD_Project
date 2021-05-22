package com.apache.converters

import com.apache.models.Colors

fun buildHEXColor(hexDef: Colors.HEX, webSafe: Boolean = false): Colors.Color = Colors.Color(Colors.Color.Mode(hexDef), webSafe)

fun buildHEXColor(code: String, webSafe: Boolean = false): Colors.Color = buildHEXColor(buildHEX(code), webSafe)

fun buildHEX(code: String): Colors.HEX = Colors.HEX(code)

fun buildRGBColor(rgbDef: Colors.RGB, webSafe: Boolean = false): Colors.Color = Colors.Color(Colors.Color.Mode(rgbDef), webSafe)

fun buildRGBColor(red: Int, green: Int, blue: Int, webSafe: Boolean = false): Colors.Color =
    buildRGBColor(buildRGB(red, green, blue), webSafe)

fun buildRGB(red: Int, green: Int, blue: Int): Colors.RGB = Colors.RGB(red, green, blue)

fun buildCMYKColor(cmykDef: Colors.CMYK, webSafe: Boolean = false): Colors.Color = Colors.Color(Colors.Color.Mode(cmykDef), webSafe)

fun buildCMYKColor(cyan: Float, magenta: Float, yellow: Float, key: Float, webSafe: Boolean = false): Colors.Color =
    buildCMYKColor(buildCMYK(cyan, magenta, yellow, key), webSafe)

fun buildCMYK(cyan: Float, magenta: Float, yellow: Float, key: Float): Colors.CMYK = Colors.CMYK(cyan, magenta, yellow, key)

fun buildHSVColor(hsvDef: Colors.HSV, webSafe: Boolean = false): Colors.Color = Colors.Color(Colors.Color.Mode(hsvDef), webSafe)

fun buildHSVColor(hue: Int, saturation: Float, value: Float, webSafe: Boolean = false): Colors.Color = buildHSVColor(
    buildHSV(hue, saturation, value), webSafe
)

fun buildHSV(hue: Int, saturation: Float, value: Float): Colors.HSV = Colors.HSV(hue, saturation, value)
