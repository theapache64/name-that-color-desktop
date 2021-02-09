package com.theapache64.tone.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColorResult(resolvedColor: com.theapache64.tone.core.model.Color?) {
    Text(
        text = resolvedColor?.name ?: "Invalid",
        style = MaterialTheme.typography.h3
    )

    Spacer(
        modifier = Modifier.height(10.dp)
    )
}