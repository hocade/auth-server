package com.board.authserver.user.domain

import com.board.authserver.role.domain.Role
import jakarta.persistence.*

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@Entity
@Table(name = "USER_ROLES")
class UserRole(
    @EmbeddedId
    val id: UserRoleId,

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    val user: User,

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    val role: Role

)