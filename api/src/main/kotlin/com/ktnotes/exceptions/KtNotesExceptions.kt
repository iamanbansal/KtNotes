package com.ktnotes.exceptions


class BadRequestException(override val message: String) : Exception(message)

class UnauthorizedActivityException(override val message: String) : Exception(message)

class NoteNotFoundException(override val message: String) : Exception(message)

class TransactionExceptions(override val message: String) : Exception(message)

