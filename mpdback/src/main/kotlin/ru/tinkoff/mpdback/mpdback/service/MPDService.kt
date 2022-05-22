package ru.tinkoff.mpdback.mpdback.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.tinkoff.mpdback.mpdback.model.UserInfo

@Service
class MPDService(
) {

//    fun newRecord(userRecord: UserRecord) =
//        recordsRepository.newRecord(userRecord)
//
//
//    fun getUser(login: String): UserInfo {
//        val user = userRepository.findUser(login) ?: return userRepository.newUser(login)
//        return UserInfo()
//    }
//
//    fun getAllRecords(login: String): List<UserRecord> {
//        return recordsRepository.readRecord(login)
//    }

    fun addRecord(userInfo: UserInfo) {

    }

}