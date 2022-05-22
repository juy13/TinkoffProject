package ru.tinkoff.mpdback.jwt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.tinkoff.mpdback.jwt.model.RegistrationUser

interface RegistrationRepository : JpaRepository<RegistrationUser, Long> {

    fun findByLogin(login: String): RegistrationUser?
    fun findByEmail(login: String): RegistrationUser?

    @Query(value = "select * from registration r where r.login = :login and r.email = :email", nativeQuery = true)
    fun find(
        @Param("login") login: String,
        @Param("email") email: String
    ): RegistrationUser?

    @Query(value = "select max(id) from registration", nativeQuery = true)
    fun max(): Long?
}
