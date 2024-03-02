package com.board.authserver.user.domain

import jakarta.persistence.*

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@Entity
@Table(name = "USER")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "NICK_NAME")
    val nickName: String,

    @Column(name = "PASSWORD")
    var password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val roles: List<UserRole>

) {
}