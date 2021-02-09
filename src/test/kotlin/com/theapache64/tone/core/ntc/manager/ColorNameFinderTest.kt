package com.theapache64.tone.core.ntc.manager

import com.theapache64.tone.core.ntc.model.HexColor
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorNameFinderTest {

    @Test
    fun `find a color`() {
        // black exact match
        assertEquals("Black", ColorNameFinder.findColor(HexColor("#000000")).second.name)
        // non exact match
        assertEquals("Black Russian", ColorNameFinder.findColor(HexColor("#000010")).second.name)
        // white exact match
        assertEquals("White", ColorNameFinder.findColor(HexColor("#FFFFFF")).second.name)
        // white with 3 letters only
        assertEquals("White", ColorNameFinder.findColor(HexColor("#FFF")).second.name)
    }
}
