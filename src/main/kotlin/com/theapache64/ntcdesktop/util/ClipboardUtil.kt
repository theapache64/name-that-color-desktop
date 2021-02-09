package com.theapache64.ntcdesktop.util

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
            if (clipboardValue.isValidColor()) {
                return clipboardValue
            }
        }

        return null
    }

    fun addToClipboard(data: String) {
        clipboardManager.setContents(StringSelection(data), null)
    }
}