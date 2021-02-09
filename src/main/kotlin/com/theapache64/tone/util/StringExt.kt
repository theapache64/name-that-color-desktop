package com.theapache64.tone.util

import com.theapache64.tone.core.ntc.model.HexColor

fun String.isValidColor(): Boolean {
    return try {
        HexColor(this)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}
