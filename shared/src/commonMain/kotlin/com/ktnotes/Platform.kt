package com.ktnotes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform