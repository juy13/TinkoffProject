package ru.tinkoff.mpdback.jwt.service

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.tinkoff.mpdback.jwt.model.RegistrationUser
import ru.tinkoff.mpdback.jwt.repository.RegistrationRepository


@Service
class JwtUserDetailsService(
    private val userRepository: RegistrationRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: RegistrationUser? = userRepository.findByLogin(username)
        return if (user != null) {
            if (user.login != "unknown") {
                User(user.login, user.password, ArrayList())
            } else {
                throw BadCredentialsException("Unknown user")
            }
        } else {
            throw BadCredentialsException("Huston, we have a problem")
        }
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}