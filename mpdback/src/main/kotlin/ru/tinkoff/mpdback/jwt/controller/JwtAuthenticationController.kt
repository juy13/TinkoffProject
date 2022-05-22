package ru.tinkoff.mpdback.jwt.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import ru.tinkoff.mpdback.jwt.config.JwtTokenUtil
import ru.tinkoff.mpdback.enums.AuthRc
import ru.tinkoff.mpdback.error.AuthOperations
import ru.tinkoff.mpdback.error.AuthUser
import ru.tinkoff.mpdback.error.AuthUserErr
import ru.tinkoff.mpdback.error.AuthUserOk
import ru.tinkoff.mpdback.jwt.model.JwtRequest
import ru.tinkoff.mpdback.jwt.model.RegistrationUser
import ru.tinkoff.mpdback.jwt.service.JWTService
import java.util.*


@RestController
@CrossOrigin
class JwtAuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtInMemoryUserDetailsService: UserDetailsService,
    private val mpdService: JWTService
) {

    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    @Throws(java.lang.Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*>? {

        when(val auth = authenticate(authenticationRequest.username, authenticationRequest.password)) {
            is AuthUserErr -> return ResponseEntity.ok(auth)
            else -> {}
        }

        val userDetails = jwtInMemoryUserDetailsService
            .loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtil.generateToken(userDetails)

        return ResponseEntity.ok(AuthUserOk(AuthRc.AuthOK, token))
    }

    @RequestMapping(value = ["/new"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun createNewUser(@RequestBody newUserRegistration: RegistrationUser): ResponseEntity<*> {
        val id = mpdService.registrationUser(newUserRegistration)
        return ResponseEntity.ok(id)
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) : AuthOperations {
        Objects.requireNonNull(username)
        Objects.requireNonNull(password)
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
//            throw Exception("USER_DISABLED", e)
            return AuthUserErr(AuthRc.AuthUserDisabled)
        } catch (e: BadCredentialsException) {
            return when (e.message) {
                "Unknown user" -> AuthUserErr(AuthRc.AuthNoUser)
                "Huston, we have a problem" -> AuthUserErr(AuthRc.AuthError)
                else -> AuthUserErr(AuthRc.AuthError)
            }
        } catch (e: Exception) {
            return when (e.message) {
                "Unknown user" -> AuthUserErr(AuthRc.AuthNoUser)
                "Huston, we have a problem" -> AuthUserErr(AuthRc.AuthError)
                else -> AuthUserErr(AuthRc.AuthError)
            }
        }
        return AuthUser(AuthRc.AuthOK)
    }
}