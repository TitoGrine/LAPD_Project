package com.proto.controllers

import com.proto.models.Colors

fun generatePalette(request: Colors.ColorPaletteRequest): Colors.ColorPaletteResponse? {
    val baseColor = request.color

    val palette: List<Colors.Color>? = when {
        baseColor.colorDef.hasHexMode() -> generateHexPalette(baseColor.colorDef.hexMode)
        baseColor.colorDef.hasRgbMode() -> generateRGBPalette(baseColor.colorDef.rgbMode)
        baseColor.colorDef.hasCmykMode() -> generateCMYKPalette(baseColor.colorDef.cmykMode)
        baseColor.colorDef.hasHsvMode() -> generateHSVPalette(baseColor.colorDef.hsvMode)
        else -> null
    }

    if (palette == null)
        return null

    return Colors.ColorPaletteResponse
        .newBuilder()
        .addAllPalette(palette)
        .build()
}

fun generateHexPalette(color: Colors.HEX): List<Colors.Color>{
    val hsvColor = HEX_2_HSV(color)
    val hsvPalette = generatePalette(hsvColor)

    return hsvPalette.map { c -> buildHEXColor(HSV_2_HEX(c))}
}

fun generateRGBPalette(color: Colors.RGB): List<Colors.Color>{
    val hsvColor = RGB_2_HSV(color)
    val hsvPalette = generatePalette(hsvColor)

    return hsvPalette.map { c -> buildRGBColor(HSV_2_RGB(c))}
}

fun generateCMYKPalette(color: Colors.CMYK): List<Colors.Color>{
    val hsvColor = CMYK_2_HSV(color)
    val hsvPalette = generatePalette(hsvColor)

    return hsvPalette.map { c -> buildCMYKColor(HSV_2_CMYK(c))}
}

fun generateHSVPalette(color: Colors.HSV): List<Colors.Color>{
    val hsvPalette = generatePalette(color)

    return hsvPalette.map { c -> buildHSVColor(c)}
}