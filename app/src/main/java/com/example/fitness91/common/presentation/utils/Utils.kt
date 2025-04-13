package com.example.fitness91.common.presentation.utils

fun String.convertStringToDouble(): Double? {
    return if (this.isNotEmpty() && this.matches(Regex("^\\d+(\\.\\d+)?$"))) {
        this.toDoubleOrNull()
    } else {
        null
    }
}

fun String.convertStringToInt(): Int? {
    return if (this.isNotEmpty() && this.matches(Regex("^\\d+$"))) {
        this.toIntOrNull()
    } else {
        null
    }
}

fun Double.roundOff2Dec(): Double {
    return Math.round(this * 100) / 100.0
}

fun Float.roundOff2Dec(): Float {
    return Math.round(this * 100) / 100.0f
}

fun Float.roundOff1DecStr(): String {
    return (Math.round(this * 10) / 10.0f).toString()
}