package com.hustcaster.app.utils

fun String.formatDescription(): String {
    val afterTrimmed = this.trim()
    val strings = afterTrimmed.split("</p>")
    val stringBuilder = StringBuilder()
    strings.forEach { str ->
        stringBuilder.append(str.removePrefix("<p>"))
        stringBuilder.append("\n\n")
    }
    return stringBuilder.toString()
}