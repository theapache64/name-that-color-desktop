package com.theapache64.ntcdesktop

import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


val nonWordCharRegEx = "\\W+".toRegex()
private val random by lazy { Random() }
const val APP_NAME = "Name That Color"

class Main




fun main() {

    var inputColorCode by mutableStateOf(ClipboardUtil.parseColorFromClipboard() ?: getRandomColor())
    var colors by mutableStateOf(listOf<Chroma>())

    Window(
        title = APP_NAME,
        size = IntSize(1024, 600),
        events = WindowEvents(
            onFocusGet = {
                val clipboardColor = ClipboardUtil.parseColorFromClipboard()
                if (clipboardColor != null) {
                    inputColorCode = clipboardColor
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

        ColorMuskTheme {
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
                },
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
    onRandomColorClicked: () -> Unit,
) {

    var showCopiedToClipboardSnackbar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var snackBarJob: Job? = null

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
            inputColorCode = inputColorCode,
            resolvedChroma = resolvedChroma,
            onInputColorCodeChanged = onInputColorCodeChanged,
            onClipboardClicked = {
                showCopiedToClipboardSnackbar = false
                onClipboardClicked(it)
                scope.launch {
                    delay(1) // just to make little blink
                    showCopiedToClipboardSnackbar = true
                }
            },
            onRandomColorClicked = {
                showCopiedToClipboardSnackbar = false // hide if showing
                onRandomColorClicked.invoke()
            }
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

        // Bottom SnackBar
        if (showCopiedToClipboardSnackbar) {
            Snackbar(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        showCopiedToClipboardSnackbar = false // dismiss when clicked
                    }
                    .align(Alignment.BottomCenter),
            ) {
                Text(text = "Copied to clipboard")
            }

            LaunchedEffect(Unit) {
                delay(2_000) // snack bar duration
                showCopiedToClipboardSnackbar = false
            }
        }
    }
}

@Composable
private fun Content(
    inputColorCode: String,
    resolvedChroma: Chroma?,
    onInputColorCodeChanged: (String) -> Unit,
    onClipboardClicked: (String) -> Unit,
    onRandomColorClicked: () -> Unit,
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
            colorCode = inputColorCode,
            onInputColorCodeChanged = onInputColorCodeChanged,
            onRandomColorClicked = onRandomColorClicked
        )

        /*Code*/
        if (resolvedChroma != null) {
            SourceCode(resolvedChroma, inputColorCode, onClipboardClicked)
        }
    }
}


fun getRandomColor(): String {
    val randNum = random.nextInt(0xffffff + 1)
    return String.format("%06x", randNum)
}

