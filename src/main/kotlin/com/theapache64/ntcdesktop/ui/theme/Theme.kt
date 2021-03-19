package com.theapache64.ntcdesktop.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color set
val LightTheme = lightColors() // TODO : Implement light theme

val DarkTheme = darkColors(
    primary = R.color.TelegramBlue,
    onPrimary = Color.White,
    secondary = R.color.BrightGray,
    onSecondary = Color.White,
    surface = R.color.WoodSmoke,
)

@Composable
fun NameThatColorTheme(
    isDark: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (isDark) DarkTheme else LightTheme,
        typography = StackzyTypography
    ) {
        Surface {
            Column {
                content()
            }
        }
    }
}