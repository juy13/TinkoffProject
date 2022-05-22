package ru.tinkoff.mpdback.jwt.model

import java.io.Serializable

data class JwtRequest(
    val username: String = "unknown",
    val password: String = "unknown"
) {
    companion object {
        private const val serialVersionUID = 5926468583005150707L
    }
}