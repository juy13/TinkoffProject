package ru.tinkoff.mpdback.enums

enum class DBrc (val rc : Int) {
    DBOk(0),
    DBExistUser(-1),
    ExistLogin(-2),
    ExistEmail(-3),
    DBNoUser(-4),
    DBNoData(-5),
    DBError(-255)

}