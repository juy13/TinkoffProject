package ru.tinkoff.mpdback.broker.service

import org.springframework.stereotype.Service
import ru.tinkoff.mpdback.broker.model.UserFileEnc
import ru.tinkoff.mpdback.broker.model.UserInfoEnc
import ru.tinkoff.mpdback.broker.repository.UserFileEncRepository
import ru.tinkoff.mpdback.broker.repository.UserInfoEncRepository
import ru.tinkoff.mpdback.jwt.repository.RegistrationRepository
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import java.nio.file.Files
import kotlin.io.path.Path


// TODO create rc for functions!!!

@Service
class FileEncryptionService(
    private val userFileEncRepository: UserFileEncRepository,
    private val registrationRepository: RegistrationRepository
) {

    fun newFile(userInfo: UserInfo) {
        val user = registrationRepository.findById(userInfo.userId)
        val pathIn = "/var/tmp/mpd/${(userInfo.userId)}/"
        val pathOut = "/var/tmp/mpdEnc/${(userInfo.userId)}/"

        if (!Files.exists(Path(pathOut))) {
            Files.createDirectory(Path(pathOut))
        }
        if (!Files.exists(Path(pathIn))) {
            return //here rc
        }

        UserFileEnc.encryptFile(pathIn + userInfo.login, pathOut + userInfo.login, user.get().password)
        userFileEncRepository.save(UserFileEnc(
            0, userInfo.userId, pathOut + userInfo.login
        ))
        Files.deleteIfExists(Path(pathIn + userInfo.login))
    }

    fun outFile(userInfo: UserInfo) {
        val user = registrationRepository.findById(userInfo.userId)
        val pathOut = "/var/tmp/mpd/${(userInfo.userId)}/"
        val pathIn = "/var/tmp/mpdEnc/${(userInfo.userId)}/"

        if (!Files.exists(Path(pathOut))) {
            Files.createDirectory(Path(pathOut))
        }
        if (!Files.exists(Path(pathIn))) {
            return //here rc
        }

        // Check File in BD

        UserFileEnc.decryptFile(pathIn + userInfo.login, pathOut + userInfo.login, user.get().password)
//        Files.deleteIfExists(Path(pathIn + user.get().login))
    }

}