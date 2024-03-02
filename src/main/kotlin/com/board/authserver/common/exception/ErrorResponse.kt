package com.board.authserver.common.exception

import java.time.LocalDateTime

/**
 * @author jinwook.kim
 * @since 3/2/24
 */
data class ErrorResponse(
    val timeStamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: Map<String, String>?
) {
    constructor(status: Int, error: String) : this(LocalDateTime.now(), status, error, emptyMap())
    constructor(status: Int, error: String, message: Map<String, String>?) : this(LocalDateTime.now(), status, error, message)
}