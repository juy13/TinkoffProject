package ru.tinkoff.mpdback.error

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import ru.tinkoff.mpdback.enums.AuthRc
import ru.tinkoff.mpdback.enums.DBrc
import ru.tinkoff.mpdback.enums.FileRc
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfoLite

sealed interface MPDError

sealed class DBOperations() : MPDError
sealed class AuthOperations() : MPDError
sealed class FileOperations() : MPDError


class DBRegistration(val rc: DBrc, val answer: Long = 0) : DBOperations()
class DBInsertData(val rc: DBrc, val answer: Long = 0) : DBOperations()
class DBFindId(val rc : DBrc, val answer: Long = 0) : DBOperations()
class DBFindData(val rc: DBrc, val answer: List<UserInfoLite>?) : DBOperations()

class AuthUser(val rc : AuthRc) : AuthOperations()
class AuthUserErr(val rc : AuthRc) : AuthOperations()
class AuthUserOk(val rc : AuthRc, val id: Long, val token : String) : AuthOperations()

class FileUpload(val rc : FileRc, val id : Long = 0) : FileOperations()
class FileDownload(val rc : FileRc, val response : ResponseEntity<*>? = null) : FileOperations()