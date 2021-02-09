package com.theapache64.ntcdesktop.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.theapache64.namethatcolor.model.Chroma

@Composable
fun ColorResult(resolvedChroma: Chroma?) {
    Text(
        text = resolvedChroma?.name ?: "That's an invalid color",
        color = if (resolvedChroma != null) Color.White else Color.White.copy(alpha = 0.3f),
        style = if (resolvedChroma != null) MaterialTheme.typography.h4 else MaterialTheme.typography.body2
    )

    Spacer(
        modifier = Modifier.height(10.dp)
    )
}