package com.theapache64.ntcdesktop.util

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.UnsupportedFlavorException

object ClipboardUtil {

    private val clipboardManager by lazy {
        Toolkit.getDefaultToolkit().systemClipboard
    }

    fun parseColorFromClipboard(): String? {
        try {
            val clipboardValue = clipboardManager.getData(DataFlavor.stringFlavor)?.toString()
            if (clipboardValue != null) {
                if (clipboardValue.isValidColor()) {
                    return clipboardValue
                }
            }

        } catch (e: UnsupportedFlavorException) {
            e.printStackTrace()
        }
        return null
    }

    fun addToClipboard(data: String) {
        clipboardManager.setContents(StringSelection(data), null)
    }
}