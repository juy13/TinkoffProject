package ru.tinkoff.mpdback.mpdbackApi.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo


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