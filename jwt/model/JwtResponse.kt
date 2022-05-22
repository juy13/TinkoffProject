package ru.tinkoff.mpdback.jwt.model

import java.io.Serializable

data class JwtResponse (val token: String) {
    companion object {
        private const val serialVersionUID = -8091879091924046844L
    }
}