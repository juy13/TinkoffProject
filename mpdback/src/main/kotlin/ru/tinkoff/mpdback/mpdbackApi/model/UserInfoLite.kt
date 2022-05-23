package ru.tinkoff.mpdback.mpdbackApi.model

import ru.tinkoff.mpdback.enums.Status
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class UserInfoLite(
    val subject : String = "unknown",
    val login : String = "unknown",
    val password : String = "unknown"
)
