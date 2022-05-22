package ru.tinkoff.mpdback.mpdback.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data")
data class UserInfo(

    @Id
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "user", nullable = false, unique = false)
    val userId : Long = 0,

    @Column(name = "subject", nullable = false, unique = false)
    val subject : String = "unknown",

    @Column(name = "login", nullable = false, unique = false)
    val login : String = "unknown",

    @Column(name = "password", nullable = false, unique = false)
    val password : String = "unknown"
)