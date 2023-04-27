package com.ktnotes.exception

data class BadRequestException(override val message:String):Exception(message)