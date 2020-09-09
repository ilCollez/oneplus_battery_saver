package com.collez.opbatterysaver.data

enum class RefreshRate (val num: Int) {
    Hz60(1),
    Hz90(2);

    fun toText(): String = when(this) {
        Hz60 -> "60"
        Hz90 -> "90"
    }

    companion object {
        fun fromInt(value: Int) = values().first { it.num == value }
    }
}