package com.theapache64.ntcdesktop.core.ntc.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import com.theapache64.ntcdesktop.core.ntc.model.Chroma
import com.theapache64.ntcdesktop.ui.theme.R

fun Chroma.getComposeDeclarationCode(): AnnotatedString {

    return with(AnnotatedString.Builder()) {
        pushStyle(SpanStyle(color = R.color.CadetBlue))
        append("val ")
        pop()

        pushStyle(SpanStyle(color = R.color.BrandyPunch))
        append(name.replace(" ", "") ?: "null")
        pop()

        pushStyle(SpanStyle(color = R.color.CadetBlue))
        append(" = Color(")
        pop()

        pushStyle(SpanStyle(color = R.color.RoyalBlue))
        append("0xff${hexCode.toLowerCase()}")
        pop()

        pushStyle(SpanStyle(color = R.color.CadetBlue))
        append(")")
        pop()

        toAnnotatedString()
    }
}