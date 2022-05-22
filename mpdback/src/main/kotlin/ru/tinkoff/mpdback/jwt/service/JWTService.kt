//package ru.tinkoff.mpdback.jwt.service
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.stereotype.Service
//import ru.tinkoff.mpdback.enums.DBrc
//import ru.tinkoff.mpdback.error.DBRegistration
//import ru.tinkoff.mpdback.jwt.model.RegistrationUser
//import ru.tinkoff.mpdback.jwt.repository.RegistrationRepository
//
//@Service
//class JWTService(
//    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
//    private val registrationRepository: RegistrationRepository,
//) {
//
//
//    fun registrationUser(registrationUser: RegistrationUser): DBRegistration {
//        val newPass = bCryptPasswordEncoder.encode(registrationUser.password)
//        when (checkUser(registrationUser)) {
//            DBrc.ExistUser -> return DBRegistration(DBrc.ExistUser)
//            DBrc.ExistLogin -> return DBRegistration(DBrc.ExistLogin)
//            DBrc.ExistEmail -> return DBRegistration(DBrc.ExistEmail)
//            else -> {}
//        }
//        val id = registrationRepository.max()
//        return if (id != null) {
//            try {
//                val user = registrationRepository.save(
//                    RegistrationUser(
//                        id.toLong()+ 1,
//                        registrationUser.login,
//                        newPass,
//                        registrationUser.email
//                    )
//                )
//                DBRegistration(DBrc.DBOk, user.id)
//            } catch (e : Exception) {
//                DBRegistration(DBrc.DBError)
//            }
//        } else {
//            DBRegistration(DBrc.DBError)
//        }
//    }
//
//    private fun checkUser(registrationUser: RegistrationUser) : DBrc {
//        val user = registrationRepository.find(registrationUser.login, registrationUser.email)
//        if (user != null)
//        {
//            return DBrc.ExistUser
//        }
//        val login = registrationRepository.findByLogin(registrationUser.login)
//        if (login != null) {
//            return DBrc.ExistLogin
//        }
//        val email = registrationRepository.findByEmail(registrationUser.email)
//        if (email != null) {
//            return DBrc.ExistEmail
//        }
//        return DBrc.DBOk
//    }
//
//}