package com.theapache64.ntcdesktop.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.theapache64.namethatcolor.model.Chroma
import com.theapache64.ntcdesktop.ui.theme.SourceCodePro
import com.theapache64.ntcdesktop.util.getComposeDeclarationCode

@Composable
fun SourceCode(
    resolvedChroma: Chroma,
    inputColorCode: String,
    onClipboardClicked: (String) -> Unit,
) {
    Spacer(
        modifier = Modifier.height(10.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        /*Code*/
        val kotlinCode = resolvedChroma.getComposeDeclarationCode(inputColorCode)
        Text(
            text = kotlinCode,
            modifier = Modifier
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(5.dp),
            fontFamily = SourceCodePro
        )

        /*Clipboard*/
        IconButton(
            onClick = {
                onClipboardClicked(kotlinCode.toString())
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ContentPaste,
                contentDescription = "Copy to clipboard"
            )
        }
    }
}