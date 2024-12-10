package com.hustcaster.app.utils

fun String.formatDescription(): String {
    return this.trim().removePrefix("<p>").removeSuffix("</p>")
}