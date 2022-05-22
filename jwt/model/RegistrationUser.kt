package ru.tinkoff.mpdback.jwt.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registration")
data class RegistrationUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) //, columnDefinition = "serial"
//    @Column()
    val id: Long = 0,

    @Column(name="login", nullable = false, unique = true)
    val login: String = "unknown",

    @Column(name = "password", nullable = false, unique = false)
    val password : String = "unknown",

    @Column(name = "email", nullable = false, unique = true)
    val email : String = "unknown"
)