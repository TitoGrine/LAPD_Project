package com.generic.models

class GenericColors {
    data class Color(
        val colorDef: Mode
    ) {
        var webSafe: Boolean = false

        constructor(
                    colorDef: Mode,
                    webSafe: Boolean
        ) : this(colorDef){
            this.webSafe = webSafe
        }

        class Mode {
            var hexMode: HEX? = null
            var rgbMode: RGB? = null
            var cmykMode: CMYK? = null
            var hsvMode: HSV? = null

            constructor(hexMode: HEX) {
                this.hexMode = hexMode
            }

            constructor(rgbMode: RGB) {
                this.rgbMode = rgbMode
            }

            constructor(cmykMode: CMYK) {
                this.cmykMode = cmykMode
            }

            constructor(hsvMode: HSV) {
                this.hsvMode = hsvMode
            }

            fun hasHexMode(): Boolean = hexMode == null
            fun hasRgbMode(): Boolean = rgbMode == null
            fun hasCmykMode(): Boolean = cmykMode == null
            fun hasHsvMode(): Boolean = hsvMode == null
        }
    }

    data class HEX(
        val code: String
    )

    data class RGB(
        val red: Int,
        val green: Int,
        val blue: Int
    )

    data class CMYK(
        val cyan: Float,
        val magenta: Float,
        val yellow: Float,
        val key: Float
    )

    data class HSV(
        val hue: Int,
        val saturation: Float,
        val value: Float
    )

    enum class ColorMode {
        HEX_MODE,
        RGB_MODE,
        CMYK_MODE,
        HSV_MODE
    }

    data class ColorConversionRequest(
        val colorMode: ColorMode,
        val color: Color
    )

    data class ColorConversionResponse(
        val colorMode: ColorMode,
        val color: Color
    )

    data class ColorPaletteRequest(
        val color: Color
    )

    data class ColorPaletteResponse(
        val palette: List<Color>
    )
}