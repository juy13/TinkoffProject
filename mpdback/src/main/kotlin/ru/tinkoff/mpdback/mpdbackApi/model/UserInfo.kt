package ru.tinkoff.mpdback.mpdbackApi.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import ru.tinkoff.mpdback.enums.Status
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
    val userId: Long = 0,

    @Column(name = "subject", nullable = true, unique = false)
    val subject: String = "unknown",

    @Column(name = "login", nullable = true, unique = false)
    val login: String = "unknown",

    @Column(name = "password", nullable = true, unique = false)
    val password: String = "unknown",

    @Column(name = "status", nullable = false, unique = false)
    val status: Status = Status.IN_DATA
) {
    companion object {
        fun cast2UserInfoLite(userInfo: UserInfo) : UserInfoLite {
            return UserInfoLite(userInfo.subject, userInfo.login, userInfo.password)
        }
    }
}