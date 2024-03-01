package com.board.authserver.user.application

import com.board.authserver.user.domain.CustomUserDetails
import com.board.authserver.user.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
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
        username?.let {
            val user = userRepository.findByNickName(it)
            return CustomUserDetails(user)
        }
        throw NotFoundException()
    }
}