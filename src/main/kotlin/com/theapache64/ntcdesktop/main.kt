package com.theapache64.ntcdesktop

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
import com.github.theapache64.namethatcolor.manager.ColorNameFinder
import com.github.theapache64.namethatcolor.model.Chroma
import com.github.theapache64.namethatcolor.model.HexColor
import com.theapache64.ntcdesktop.ui.composables.ColorHistory
import com.theapache64.ntcdesktop.ui.composables.ColorInput
import com.theapache64.ntcdesktop.ui.composables.ColorResult
import com.theapache64.ntcdesktop.ui.composables.SourceCode
import com.theapache64.ntcdesktop.ui.theme.ColorMuskTheme
import com.theapache64.ntcdesktop.util.ClipboardUtil
import com.theapache64.ntcdesktop.util.isValidColor
import java.util.*

private val nonWordCharRegEx = "\\W+".toRegex()
private val random by lazy { Random() }
const val APP_NAME = "Name That Color"

fun main() {

    var inputColorCode by mutableStateOf(ClipboardUtil.parseColorFromClipboard() ?: getRandomColor())
    var colors by mutableStateOf(listOf<Chroma>())

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
                resolvedChroma = resolvedColor,
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
                },
                onRandomColorClicked = {
                    inputColorCode = getRandomColor()
                }
            )
        }

    }
}

@Composable
private fun MainScreen(
    inputColorCode: String,
    colors: List<Chroma>,
    resolvedChroma: Chroma?,
    onInputColorCodeChanged: (String) -> Unit,
    onClipboardClicked: (String) -> Unit,
    onColorClicked: (Chroma) -> Unit,
    onRandomColorClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = resolvedChroma?.rgb?.let { rgb ->
                    Color(rgb.r, rgb.g, rgb.b)
                } ?: MaterialTheme.colors.surface
            ),
        contentAlignment = Alignment.Center
    ) {

        Content(
            colorCode = inputColorCode,
            resolvedChroma = resolvedChroma,
            onInputColorCodeChanged = onInputColorCodeChanged,
            onClipboardClicked = onClipboardClicked,
            onRandomColorClicked = onRandomColorClicked
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
    resolvedChroma: Chroma?,
    onInputColorCodeChanged: (String) -> Unit,
    onClipboardClicked: (String) -> Unit,
    onRandomColorClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(30.dp))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Result*/
        ColorResult(resolvedChroma)

        /*Color input*/
        ColorInput(
            colorCode = colorCode,
            onInputColorCodeChanged = onInputColorCodeChanged,
            onRandomColorClicked = onRandomColorClicked
        )

        /*Code*/
        if (resolvedChroma != null) {
            SourceCode(resolvedChroma, onClipboardClicked)
        }
    }
}


fun getRandomColor(): String {
    val randNum = random.nextInt(0xffffff + 1)
    return String.format("%06x", randNum)
}

