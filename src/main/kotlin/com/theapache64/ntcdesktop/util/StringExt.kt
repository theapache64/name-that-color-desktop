package com.theapache64.ntcdesktop.util

import com.theapache64.ntcdesktop.core.ntc.model.HexColor

fun String.isValidColor(): Boolean {
    return try {
        HexColor(this)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}
