package com.board.authserver.auth.persistence

import com.board.authserver.auth.domain.CustomClient
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author jinwook.kim
 * @since 3/7/24
 */
interface CustomClientRepository : JpaRepository<CustomClient, String> {
    fun findByClientId(clientId: String): Optional<CustomClient>
}