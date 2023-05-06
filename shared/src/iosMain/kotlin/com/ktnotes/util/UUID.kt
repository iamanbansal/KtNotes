package com.ktnotes.util

import platform.Foundation.NSUUID

actual fun randomUUID() = NSUUID().UUIDString()