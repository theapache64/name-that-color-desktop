package com.theapache64.tone.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ColorInput(
    colorCode: String,
    onInputColorCodeChanged: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#",
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
            style = MaterialTheme.typography.h4,
        )
        Spacer(
            modifier = Modifier.width(5.dp)
        )

        TextField(
            modifier = Modifier.height(50.dp),
            activeColor = MaterialTheme.colors.onSurface,
            value = colorCode,
            onValueChange = {
                onInputColorCodeChanged(it)
            }
        )
    }
}

