package ru.tinkoff.mpdback.mpdbackApi.service

import org.springframework.stereotype.Service
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.repository.UserInfoRepository
//import ru.tinkoff.mpdback.jwt

@Service
class MPDService(private val userInfoRepository: UserInfoRepository,
                 private val registrationRepository: RegistrationRepository
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
        userInfoRepository.save(userInfo)
    }

    private fun testUserId(userId : Long) {

    }

}