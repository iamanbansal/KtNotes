package com.ktnotes.util

import java.util.UUID

actual fun randomUUID() = UUID.randomUUID().toString()