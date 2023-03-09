package com.ktnotes.util


val emailRegex = Regex(
    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}(\\\\.[A-Za-z]{2,})?\\\$"
)

fun String.isValidEmail(): Boolean {
    return emailRegex.matches(this)
}