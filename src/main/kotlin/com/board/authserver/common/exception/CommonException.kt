package com.board.authserver.common.exception

import com.board.authserver.common.exception.enum.CommonExceptionCode

/**
 * @author jinwook.kim
 * @since 3/2/24
 */
class CommonException(
    val exceptionCode: CommonExceptionCode
) : RuntimeException() {
}