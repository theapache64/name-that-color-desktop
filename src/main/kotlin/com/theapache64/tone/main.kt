package com.theapache64.tone

import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.theapache64.tone.composables.ColorInput
import com.theapache64.tone.composables.ColorResult
import com.theapache64.tone.composables.SourceCode
import com.theapache64.tone.core.manager.ColorNameFinder
import com.theapache64.tone.core.ntc.model.HexColor
import com.theapache64.tone.theme.ColorMusk
import com.theapache64.tone.util.ClipboardUtil

private val nonWordCharRegEx = "\\W+".toRegex()


const val APP_NAME = "Color Musk"

fun main() {

    var inputColorCode by mutableStateOf("f00")

    Window(
        title = APP_NAME,
        undecorated = true,
        size = IntSize(1024, 600),
        events = WindowEvents(
            onFocusGet = {
                val clipboardColor = ClipboardUtil.parseColorFromClipboard()
                if (clipboardColor != null) {
                    inputColorCode = clipboardColor.replace(nonWordCharRegEx, "")
                }
            }
        )
    ) {

        val resolvedColor by derivedStateOf {
            if (inputColorCode.isValidColor()) {
                ColorNameFinder.findColor(HexColor(inputColorCode)).second
            } else {
                null
            }
        }

        ColorMusk(
            title = APP_NAME
        ) {
            MainScreen(
                inputColorCode = inputColorCode,
                resolvedColor = resolvedColor,
                onInputColorCodeChanged = {
                    inputColorCode = it.replace(
                        nonWordCharRegEx,
                        ""
                    )
                },
                onClipboardClicked = { kotlinCode ->
                    ClipboardUtil.addToClipboard(kotlinCode)
                }
            )
        }

    }
}

@Composable
private fun MainScreen(
    inputColorCode: String,
    resolvedColor: com.theapache64.tone.core.model.Color?,
    onInputColorCodeChanged: (String) -> Unit,
    onClipboardClicked: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = resolvedColor?.rgb?.let { rgb ->
                    Color(rgb.r, rgb.g, rgb.b)
                } ?: MaterialTheme.colors.surface
            ),
        contentAlignment = Alignment.Center
    ) {

        Content(
            inputColorCode,
            resolvedColor,
            onInputColorCodeChanged,
            onClipboardClicked
        )
    }
}

@Composable
private fun Content(
    colorCode: String,
    resolvedColor: com.theapache64.tone.core.model.Color?,
    onInputColorCodeChanged: (String) -> Unit,
    onClipboardClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(30.dp))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Result*/
        ColorResult(resolvedColor)

        /*Color input*/
        ColorInput(
            colorCode = colorCode,
            onInputColorCodeChanged = onInputColorCodeChanged
        )

        /*Code*/
        if (resolvedColor != null) {
            SourceCode(resolvedColor, onClipboardClicked)
        }
    }
}


private fun String.isValidColor(): Boolean {
    return try {
        HexColor(this)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}
