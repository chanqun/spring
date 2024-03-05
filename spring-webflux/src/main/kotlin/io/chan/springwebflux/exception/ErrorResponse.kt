package io.chan.springwebflux.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)
