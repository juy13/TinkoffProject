package ru.tinkoff.mpdback.mpdbackApi.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data")
data class UserInfo(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "userId", nullable = false, unique = false)
    val userId : Long = 0,

    @Column(name = "subject", nullable = false, unique = false)
    val subject : String = "unknown",

    @Column(name = "login", nullable = false, unique = false)
    val login : String = "unknown",

    @Column(name = "password", nullable = false, unique = false)
    val password : String = "unknown"
)