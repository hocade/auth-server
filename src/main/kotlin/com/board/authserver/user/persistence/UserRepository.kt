package com.board.authserver.user.persistence

import com.board.authserver.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
interface UserRepository : JpaRepository<User, Long> {
    fun findByNickName(nickname: String): User
}