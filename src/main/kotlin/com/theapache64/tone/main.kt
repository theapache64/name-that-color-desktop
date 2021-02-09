package com.theapache64.tone

import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.theapache64.tone.core.manager.ColorNameFinder
import com.theapache64.tone.core.ntc.model.HexColor
import com.theapache64.tone.ui.composables.ColorHistory
import com.theapache64.tone.ui.composables.ColorInput
import com.theapache64.tone.ui.composables.ColorResult
import com.theapache64.tone.ui.composables.SourceCode
import com.theapache64.tone.ui.theme.ColorMuskTheme
import com.theapache64.tone.util.ClipboardUtil
import com.theapache64.tone.util.isValidColor
import com.theapache64.tone.core.model.Color as Kolor

private val nonWordCharRegEx = "\\W+".toRegex()


const val APP_NAME = "Color Musk"

fun main() {

    var inputColorCode by mutableStateOf("f00")
    var colors by mutableStateOf(listOf<Kolor>())

    Window(
        title = APP_NAME,
        undecorated = true,
        size = IntSize(1024, 600),
        events = WindowEvents(
            onFocusGet = {
                val clipboardColor = ClipboardUtil.parseColorFromClipboard()
                if (clipboardColor != null) {
                    val resolvedColor = ColorNameFinder.findColor(HexColor(clipboardColor))
                    if (colors.contains(resolvedColor.second).not()) {
                        inputColorCode = clipboardColor.replace(nonWordCharRegEx, "")
                    }
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

        if (resolvedColor != null && colors.contains(resolvedColor).not()) {
            colors = colors.toMutableList().apply {
                add(0, resolvedColor!!)
            }
        }

        ColorMuskTheme(
            title = APP_NAME
        ) {
            MainScreen(
                inputColorCode = inputColorCode,
                colors = colors,
                resolvedColor = resolvedColor,
                onInputColorCodeChanged = {
                    inputColorCode = it.replace(
                        nonWordCharRegEx,
                        ""
                    )

                },
                onClipboardClicked = { kotlinCode ->
                    ClipboardUtil.addToClipboard(kotlinCode)
                },
                onColorClicked = {
                    inputColorCode = it.hexCode.replace(
                        nonWordCharRegEx,
                        ""
                    ).toLowerCase()
                }
            )
        }

    }
}

@Composable
private fun MainScreen(
    inputColorCode: String,
    colors: List<Kolor>,
    resolvedColor: com.theapache64.tone.core.model.Color?,
    onInputColorCodeChanged: (String) -> Unit,
    onClipboardClicked: (String) -> Unit,
    onColorClicked: (Kolor) -> Unit
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

        if (colors.isNotEmpty()) {
            ColorHistory(
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp)
                    .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                    .align(Alignment.TopEnd),
                colors = colors,
                onColorClicked = {
                    onColorClicked(it)
                }
            )
        }
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


