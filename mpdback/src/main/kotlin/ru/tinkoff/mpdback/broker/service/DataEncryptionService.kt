package ru.tinkoff.mpdback.broker.service


import org.springframework.stereotype.Service
import ru.tinkoff.mpdback.broker.model.UserInfoEnc
import ru.tinkoff.mpdback.broker.repository.UserInfoEncRepository
import ru.tinkoff.mpdback.enums.Type
import ru.tinkoff.mpdback.jwt.repository.RegistrationRepository
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo


@Service
class DataEncryptionService(
    private val userInfoEncRepository: UserInfoEncRepository,
    private val registrationRepository: RegistrationRepository
) {

    fun newData(userInfo: UserInfo) {
        val user = registrationRepository.findById(userInfo.userId)
        userInfoEncRepository.save(
            UserInfoEnc.cast2UserInfoEnc(
                userInfo,
                user.get().password
            )
        )
    }

    fun outData(userInfo: UserInfo): List<UserInfo> {
        val user = registrationRepository.findById(userInfo.userId)

        if (userInfo.subject == "") {
            val userEnc =
                userInfoEncRepository.findByUserId(userInfo.userId)
            if (userEnc != null) {
                val userInfoOut = userEnc.map {
                    UserInfoEnc.cast2UserInfo(
                        it, user.get().password
                    )
                }
                return userInfoOut
            }
        } else {
            val userEnc =
                userInfoEncRepository.findByIdAndSubject(
                    userInfo.userId,
                    UserInfoEnc.encryptSubject(userInfo.subject, user.get().password)
                )
            if (userEnc != null) {
                val userInfoOut = userEnc.map {
                    UserInfoEnc.cast2UserInfo(
                        it, user.get().password
                    )
                }
                return userInfoOut
            }
        }
        return listOf(UserInfo())
    }

}