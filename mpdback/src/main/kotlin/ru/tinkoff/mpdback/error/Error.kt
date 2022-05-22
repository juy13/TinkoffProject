package ru.tinkoff.mpdback.error

import ru.tinkoff.mpdback.enums.AuthRc
import ru.tinkoff.mpdback.enums.DBrc

sealed interface Error

sealed class DBOperations() : Error
sealed class AuthOperations() : Error

class DBRegistration(val rc: DBrc,
                     val answer: Long = 0) : DBOperations()

class AuthUser(val rc : AuthRc) : AuthOperations()
class AuthUserErr(val rc : AuthRc) : AuthOperations()
class AuthUserOk(val rc : AuthRc, val token : String) : AuthOperations()