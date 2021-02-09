package com.theapache64.tone.util

import com.theapache64.tone.isValidColor
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

object ClipboardUtil {
    private val clipboardManager by lazy {
        Toolkit.getDefaultToolkit().systemClipboard
    }

    fun parseColorFromClipboard(): String? {
        val clipboardValue = clipboardManager.getData(DataFlavor.stringFlavor)?.toString()
        if (clipboardValue != null) {
            // Supports #AAA #AABBCC formats only
            if (isValidColor(clipboardValue)) {
                return clipboardValue
            }
        }

        return null
    }

    fun addToClipboard(data: String) {
        clipboardManager.setContents(StringSelection(data), null)
    }
}