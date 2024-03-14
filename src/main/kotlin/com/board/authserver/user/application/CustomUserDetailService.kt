package com.board.authserver.user.application

import com.board.authserver.common.exception.CommonException
import com.board.authserver.common.exception.enum.CommonExceptionCode
import com.board.authserver.user.domain.CustomUserDetails
import com.board.authserver.user.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        // todo : Filter 단에서 먼저 예외처리가 되어 @RestControllerAdvice 적용이 안됨
        val user = username?.let { userRepository.findByEmail(it) } ?: throw CommonException(CommonExceptionCode.USER_NOT_FOUND)
        return CustomUserDetails(user)
    }
}