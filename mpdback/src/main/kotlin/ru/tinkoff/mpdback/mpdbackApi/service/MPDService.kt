package ru.tinkoff.mpdback.mpdbackApi.service

import com.google.gson.Gson
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.jms.core.JmsTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.tinkoff.mpdback.enums.AuthRc
import ru.tinkoff.mpdback.enums.DBrc
import ru.tinkoff.mpdback.enums.FileRc
import ru.tinkoff.mpdback.enums.Status
import ru.tinkoff.mpdback.error.*
import ru.tinkoff.mpdback.jwt.config.JwtTokenUtil
import ru.tinkoff.mpdback.jwt.repository.RegistrationRepository
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfoLite
import ru.tinkoff.mpdback.mpdbackApi.repository.UserInfoRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import kotlin.io.path.Path


// TODO in future getListOfAllFiles

@Service
class MPDService(
    private val userInfoRepository: UserInfoRepository,
    private val registrationRepository: RegistrationRepository,
    private val jwtTokenUtil: JwtTokenUtil
) {

    fun addRecord(token: String, userInfo: UserInfo): MPDError {

        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }

        return if ((userId as DBFindId).answer != userInfo.userId) {
            DBInsertData(DBrc.DBError)
        } else {
            DBInsertData(
                DBrc.DBOk, userInfoRepository.save(
                    UserInfo(
                        userInfo.id, userInfo.userId, userInfo.subject, userInfo.login,
                        userInfo.password, Status.IN_DATA
                    )
                ).id
            )
        }
    }

    fun prepareBySubject(token: String, subject: String): MPDError {

        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }

        return try {
            val id = userInfoRepository.save(
                UserInfo(0, (userId as DBFindId).answer, subject, "", "", Status.OUT_DATA_PREPARE)
            ).id
            DBInsertData(DBrc.DBOk, id)
        } catch (e: Exception) {
            DBInsertData(DBrc.DBError)
        }
    }

    fun prepareFileByName(token: String, name: String): MPDError {

        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }

        return try {
            val id = userInfoRepository.save(
                UserInfo(
                    0, (userId as DBFindId).answer,
                    "", name, "", Status.FILE_OUT_PREPARE
                )
            ).id
            DBInsertData(DBrc.DBOk, id)
        } catch (e: Exception) {
            DBInsertData(DBrc.DBError)
        }
    }

    fun prepareAll(token: String): MPDError {

        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }

        return try {
            val id = userInfoRepository.save(
                UserInfo(0, (userId as DBFindId).answer, "", "", "", Status.OUT_DATA_PREPARE)
            ).id
            DBInsertData(DBrc.DBOk, id)
        } catch (e: Exception) {
            DBInsertData(DBrc.DBError)
        }
    }

    fun getAll(token: String): MPDError {
        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }

        val data = userInfoRepository.findReadyById((userId as DBFindId).answer, Status.OUT_DATA.status)
        if (data != null) {
            val info = mutableListOf<UserInfoLite>()
            data.forEach {
                info.add(UserInfo.cast2UserInfoLite(it))
                userInfoRepository.deleteById(it.id)
            }
            if (info.isEmpty()) {
                return DBFindData(DBrc.DBNoData, listOf())
            }
            return DBFindData(DBrc.DBOk, info)
        }
        return DBFindData(DBrc.DBError, listOf())
    }

    fun uploadFile(token: String, file: MultipartFile): MPDError {

        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }


        // size in bytes
        if (file.size > 24400005) {
            return FileUpload(FileRc.FileBig)
        }

        val path = "/var/tmp/mpd/${((userId as DBFindId).answer)}/"

        if (!Files.exists(Path(path))) {
            Files.createDirectory(Path(path))
        }
        val convertFile = File(path + file.originalFilename)
        convertFile.createNewFile()
        val fout = FileOutputStream(convertFile)
        fout.write(file.bytes)
        fout.close()

        return try {
            val fileName = file.originalFilename
            return if (fileName != null) {
                val id = userInfoRepository.save(
                    UserInfo(0, (userId as DBFindId).answer, "", fileName, "", Status.FILE_IN)
                ).id
                FileUpload(FileRc.FileOk, id)
            } else {
                return FileUpload(FileRc.FileError)
            }
        } catch (e: Exception) {
            DBInsertData(DBrc.DBError)
        }
    }

    fun downLoadFile(token: String, name: String): MPDError {

        val userId = withAuth(token)
        when (userId) {
            is DBFindId -> {
                if (userId.rc == DBrc.DBError)
                    return userId
            }
            else -> {}
        }

        val path = "/var/tmp/mpd/${((userId as DBFindId).answer)}/"

        if (!Files.exists(Path(path))) {
            return FileDownload(FileRc.FileError)
        }

        return try {
            val data = userInfoRepository.findReadyFileByName(userId.answer, Status.FILE_OUT.status, name)
            if (data != null) {
                if (data.isEmpty() || data.size > 1) {
                    DBFindData(DBrc.DBError, listOf())
                } else {
                    data.forEach {
                        userInfoRepository.deleteById(it.id)
                    }
                    val file = File(path + name)
                    val resource = InputStreamResource(FileInputStream(file))
                    val headers = HttpHeaders()
                    headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.name))
                    headers.add("Cache-Control", "no-cache, no-store, must-revalidate")
                    headers.add("Pragma", "no-cache")
                    headers.add("Expires", "0")
                    val resp = ResponseEntity.ok().headers(headers).contentLength(
                        file.length()
                    ).contentType(MediaType.parseMediaType("application/txt")).body<Any>(resource)
                    FileDownload(FileRc.FileOk, resp)
                }
            } else {
                DBFindData(DBrc.DBError, listOf())
            }
        } catch (e: Exception) {
            DBFindData(DBrc.DBError, listOf())
        }

    }


    private fun withAuth(token: String): MPDError {
        return when (val user = validateUser(token)) {
            is DBFindId -> {
                return when (user.rc) {
                    DBrc.DBExistUser -> {
                        user
                    }
                    else -> {
                        DBInsertData(DBrc.DBError)
                    }
                }
            }
            else -> {
                AuthUserErr(AuthRc.AuthError)
            }
        }
    }


    private fun validateUser(token: String): MPDError {
        if (token.startsWith("Bearer ")) {
            val jwtToken = token.substring(7)
            return try {
                val username = jwtTokenUtil.getUsernameFromToken(jwtToken)
                val user = registrationRepository.findByLogin(username)
                if (user != null) {
                    DBFindId(DBrc.DBExistUser, user.id)
                } else {
                    DBFindId(DBrc.DBNoUser)
                }
            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
                AuthUserErr(AuthRc.AuthError)
            } catch (e: ExpiredJwtException) {
                AuthUserErr(AuthRc.AuthTokenOut)
            }
        } else {
            return DBFindId(DBrc.DBError)
        }
    }

    private fun testUserId(userId: Long): DBrc {
        val user = registrationRepository.findById(userId)
        return if (!user.isEmpty) {
            DBrc.DBExistUser
        } else {
            DBrc.DBNoUser
        }
    }

}