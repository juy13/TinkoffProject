package ru.tinkoff.mpdback.enums

enum class DBrc (val rc : Int) {
    ExistUser(-1),
    ExistLogin(-2),
    ExistEmail(-3),
    DBError(-255),
    DBOk(0)
}