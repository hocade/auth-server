package com.board.authserver.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@Embeddable
data class UserRoleId(
    @Column(name = "USER_ID")
    val userId: Long,
    @Column(name = "ROLE_ID")
    val roleId: Long
) : Serializable