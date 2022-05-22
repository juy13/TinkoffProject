package ru.tinkoff.mpdback.mpdback.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.tinkoff.mpdback.jwt.model.RegistrationUser
import ru.tinkoff.mpdback.mpdback.model.UserInfo


interface UserInfoRepository : JpaRepository<UserInfo, Long> {

//    fun findByLogin(login: String): UserInfo?
//    fun findByEmail(login: String): UserInfo?
//
//    @Query(value = "select * from registration r where r.login = :login and r.email = :email", nativeQuery = true)
//    fun find(
//        @Param("login") login: String,
//        @Param("email") email: String
//    ): UserInfo?
//
//    @Query(value = "select max(id) from registration", nativeQuery = true)
//    fun max(): Long?
}