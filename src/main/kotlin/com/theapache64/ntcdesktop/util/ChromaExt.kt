package com.theapache64.ntcdesktop.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import com.github.theapache64.namethatcolor.model.Chroma
import com.theapache64.ntcdesktop.ui.theme.R

fun Chroma.getComposeDeclarationCode(inputColorCode : String): AnnotatedString {

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
        append("0xff${inputColorCode.toLowerCase()}")
        pop()

        pushStyle(SpanStyle(color = R.color.CadetBlue))
        append(")")
        pop()

        toAnnotatedString()
    }
}