package com.theapache64.ntcdesktop.util

import com.github.theapache64.namethatcolor.model.HexColor


fun String.isValidColor(): Boolean {
    return try {
        HexColor(this)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}
