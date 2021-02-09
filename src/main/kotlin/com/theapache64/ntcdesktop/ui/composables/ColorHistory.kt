package com.theapache64.ntcdesktop.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import com.github.theapache64.namethatcolor.model.Chroma

/*fun main() {
    Window {
        ColorMuskTheme {
            ColorStorage(
                setOf(
                    Color("#ff0000", "Red", Rgb(255, 0, 0), Hsl(0, 0, 0)),
                    Color("#00ff00", "Green", Rgb(0, 255, 0), Hsl(0, 0, 0)),
                    Color("#00ff00", "Blue", Rgb(0, 0, 255), Hsl(0, 0, 0)),
                )
            )
        }
    }
}*/

@Composable
fun ColorHistory(
    modifier: Modifier = Modifier,
    colors: List<Chroma>,
    onColorClicked: (Chroma) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(colors.toList()) { item ->
            ColorItem(
                color = item,
                onColorClicked = onColorClicked
            )
        }
    }
}

private const val DEFAULT_COLOR_ITEM_BG_ALPHA = 0.2f

@Composable
fun ColorItem(
    color: Chroma,
    onColorClicked: (Chroma) -> Unit
) {
    var backgroundAlpha by remember { mutableStateOf(DEFAULT_COLOR_ITEM_BG_ALPHA) }

    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.Black.copy(alpha = backgroundAlpha), RoundedCornerShape(5.dp))
            .clickable {
                onColorClicked(color)
            }
            .pointerMoveFilter(
                onEnter = {
                    backgroundAlpha = 0.5f
                    false
                },
                onExit = {
                    backgroundAlpha = DEFAULT_COLOR_ITEM_BG_ALPHA
                    false
                }
            )
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = color.name, modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.body2)
        Box(
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterVertically)
                .background(
                    Color(
                        color.rgb.r,
                        color.rgb.g,
                        color.rgb.b,
                    ), RoundedCornerShape(percent = 50)
                )
        )
    }
}