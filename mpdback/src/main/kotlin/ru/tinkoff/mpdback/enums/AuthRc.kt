package ru.tinkoff.mpdback.enums

enum class AuthRc (val rc : Int) {
    AuthOK(0),
    AuthNoUser(-1),
    AuthUserDisabled(-2),
    AuthTokenOut(-3),
    AuthError(-255)
}