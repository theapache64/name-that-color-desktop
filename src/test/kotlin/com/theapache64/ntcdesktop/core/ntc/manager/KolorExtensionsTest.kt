package com.theapache64.ntcdesktop.core.ntc.manager

import com.theapache64.ntcdesktop.core.ntc.model.Hsl
import com.theapache64.ntcdesktop.core.ntc.model.Rgb
import com.theapache64.ntcdesktop.core.ntc.util.hsl
import com.theapache64.ntcdesktop.core.ntc.util.rgb
import org.junit.Assert.assertEquals
import org.junit.Test

class ChromaExtensionsTest {

    @Test
    fun `color to rgb`() {
        // black
        assertEquals(Rgb(0, 0, 0), "000000".rgb())
        // white
        assertEquals(Rgb(255, 255, 255), "FFFFFFF".rgb())
        // red
        assertEquals(Rgb(255, 0, 0), "FF0000".rgb())
        // green
        assertEquals(Rgb(0, 255, 0), "00FF00".rgb())
        // blue
        assertEquals(Rgb(0, 0, 255), "0000FF".rgb())
        // random color with different values
        assertEquals(Rgb(141, 144, 161), "8D90A1".rgb())
    }

    @Test
    fun `color input to hsl`() {
        // black
        assertEquals(Hsl(0, 0, 0), "000000".hsl())
        // white
        assertEquals(Hsl(0, 0, 100), "FFFFFFF".hsl())
        // random color with different values
        assertEquals(Hsl(231, 10, 59), "8D90A1".hsl())
        // some other random color with different values
        assertEquals(Hsl(35, 88, 51), "f09414".hsl())
        // some other random color with different values
        assertEquals(Hsl(254, 46, 11), "150f29".hsl())
    }
}
