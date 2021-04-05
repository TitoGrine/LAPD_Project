package com.proto.controllers

import com.proto.models.Colors
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

fun HEX_2_HEX(hex: Colors.HEX): Colors.HEX {
    return hex
}

fun HEX_2_RGB(hex: Colors.HEX): Colors.RGB {
    //TODO

    return buildRGB(0, 0, 0)
}

fun HEX_2_CMYK(hex: Colors.HEX): Colors.CMYK {
    return RGB_2_CMYK(HEX_2_RGB(hex))
}

fun HEX_2_HSV(hex: Colors.HEX): Colors.HSV {
    return RGB_2_HSV(HEX_2_RGB(hex))
}

fun RGB_2_HEX(rgb: Colors.RGB): Colors.HEX {
    val red = rgb.red
    val green = rgb.green
    val blue = rgb.blue

    val code = """#${red.toString(16)}${green.toString(16)}${blue.toString(16)}"""

    return buildHEX(code.toUpperCase())
}

fun RGB_2_RGB(rgb: Colors.RGB): Colors.RGB {
    return rgb
}

fun RGB_2_CMYK(rgb: Colors.RGB): Colors.CMYK {
    val red = rgb.red
    val green = rgb.green
    val blue = rgb.blue

    val Rc: Float = (red / 255.0).toFloat()
    val Gc: Float = (green / 255.0).toFloat()
    val Bc: Float = (blue / 255.0).toFloat()

    val K = 1 - max(max(Rc, Gc), Bc)

    if(1.0.compareTo(K) == 0)
        return buildCMYK(0.toFloat(),0.toFloat(),0.toFloat(), K)

    val cyan = (1 - Rc - K) / (1 - K)
    val magenta = (1 - Gc - K) / (1 - K)
    val yellow = (1 - Bc - K) / (1 - K)

    return buildCMYK(cyan, magenta, yellow, K)
}

fun RGB_2_HSV(rgb: Colors.RGB): Colors.HSV {
    val red = rgb.red
    val green = rgb.green
    val blue = rgb.blue

    val Rc: Float = (red / 255.0).toFloat()
    val Gc: Float = (green / 255.0).toFloat()
    val Bc: Float = (blue / 255.0).toFloat()

    val minVal = min(min(Rc, Gc), Bc)
    val maxVal = max(max(Rc, Gc), Bc)
    val delta = maxVal - minVal

    val value = maxVal

    if(0.0.compareTo(delta) == 0)
        return buildHSV(0, 0.toFloat(), value)

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

    return buildHSV(hue.roundToInt(), saturation, value)
}

fun CMYK_2_HEX(cmyk: Colors.CMYK): Colors.HEX {
    return RGB_2_HEX(CMYK_2_RGB(cmyk))
}

fun CMYK_2_RGB(cmyk: Colors.CMYK): Colors.RGB {
    //TODO

    return buildRGB(0, 0, 0)
}

fun CMYK_2_CMYK(cmyk: Colors.CMYK): Colors.CMYK {
    return cmyk
}

fun CMYK_2_HSV(cmyk: Colors.CMYK): Colors.HSV {
    return RGB_2_HSV(CMYK_2_RGB(cmyk))
}

fun HSV_2_HEX(hsv: Colors.HSV): Colors.HEX {
    return RGB_2_HEX(HSV_2_RGB(hsv))
}

fun HSV_2_RGB(hsv: Colors.HSV): Colors.RGB {
    //TODO

    return buildRGB(0, 0, 0)
}

fun HSV_2_CMYK(hsv: Colors.HSV): Colors.CMYK {
    return RGB_2_CMYK(HSV_2_RGB(hsv))
}

fun HSV_2_HSV(hsv: Colors.HSV): Colors.HSV {
    return hsv
}