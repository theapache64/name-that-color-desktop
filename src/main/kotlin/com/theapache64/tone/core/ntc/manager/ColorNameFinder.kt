package com.theapache64.tone.core.manager

import com.theapache64.tone.core.exception.ColorNotFoundException
import com.theapache64.tone.core.model.Color
import com.theapache64.tone.core.ntc.model.HexColor
import com.theapache64.tone.core.util.colorsMaterialNames
import com.theapache64.tone.core.util.colorsNames
import com.theapache64.tone.core.util.hsl
import com.theapache64.tone.core.util.rgb
import kotlin.math.pow

/**
 * Class which loads all the hex codes and names and prepare the RGB and HSL values to be searched for an exact or closest match
 * Based on http://chir.ag/projects/ntc/ntc.js
 */
object ColorNameFinder {

    private var colors: List<Color> = colorsNames.map { entry -> Color(entry.key, entry.value, entry.key.rgb(), entry.key.hsl()) }
    private var materialColors: List<Color> = colorsMaterialNames.map { entry -> Color(entry.key, entry.value, entry.key.rgb(), entry.key.hsl()) }

    /**
     * look for the Color of an hexadecimal color
     */
    fun findColor(color: HexColor) = find(color, colors)

    /**
     * look for the Color of an hexadecimal material color
     */
    fun findMaterialColor(color: HexColor) = find(color, materialColors)

    /**
     * look for the Color of an hexadecimal color
     */
    private fun find(color: HexColor, colors: List<Color>): Pair<HexColor, Color> {

        val (r, g, b) = color.rgb()
        val (h, s, l) = color.hsl()

        var cl = -1
        var df = -1

        colors.forEachIndexed { index, col ->

            if (color.value == col.hexCode) return color to col
            else {
                val ndf1 = (r - col.rgb.r).toDouble().pow(2.0).toInt() + (g - col.rgb.g).toDouble().pow(2.0).toInt() + (b - col.rgb.b).toDouble().pow(2.0).toInt()
                val ndf2 = (h - col.hsl.h).toDouble().pow(2.0).toInt() + (s - col.hsl.s).toDouble().pow(2.0).toInt() + (l - col.hsl.l).toDouble().pow(2.0).toInt()
                val ndf = ndf1 + ndf2 * 2
                if (df < 0 || df > ndf) {
                    df = ndf
                    cl = index
                }
            }
        }

        // if not found a close by one, we return an error
        if (cl < 0) throw ColorNotFoundException()
        // if found, return the name
        return color to colors[cl]
    }
}
