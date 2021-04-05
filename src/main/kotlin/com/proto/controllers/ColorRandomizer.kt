package com.proto.controllers

import com.proto.models.Colors
import kotlin.random.Random

fun getRandomHexColor(): Colors.HEX {
    val hexCode = CharArray(6)

    for (i in hexCode.indices) {
        val randCode = (48..63).random()
        hexCode[i] = (if (randCode > 57) randCode + 7 else randCode).toChar()
        print(hexCode[i])
    }

    return Colors.HEX.newBuilder()
        .setCode("#$hexCode")
        .build()
}

fun getRandomRGBColor(): Colors.RGB {
    return Colors.RGB.newBuilder()
        .setRed((0..255).random())
        .setGreen((0..255).random())
        .setBlue((0..255).random())
        .build()
}

fun getRandomCMYKColor(): Colors.CMYK {
    return Colors.CMYK.newBuilder()
        .setCyan(Random.nextFloat())
        .setMagenta(Random.nextFloat())
        .setYellow(Random.nextFloat())
        .setKey(Random.nextFloat())
        .build()
}

fun getRandomHSVColor(): Colors.HSV {
    return Colors.HSV.newBuilder()
        .setHue((0..360).random())
        .setSaturation(Random.nextFloat())
        .setValue(Random.nextFloat())
        .build()
}
