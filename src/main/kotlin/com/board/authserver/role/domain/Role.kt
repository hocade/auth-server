package com.board.authserver.role.domain

import com.board.authserver.role.application.enum.RoleType
import jakarta.persistence.*

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@Entity
@Table(name = "ROLE")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "NAME")
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    val type: RoleType,
) {
}