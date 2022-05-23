package ru.tinkoff.mpdback.mpdbackApi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo


@Transactional
interface UserInfoRepository : JpaRepository<UserInfo, Long> {

    @Query(
        value = "select * from data d where d.user_id = :user and d.subject = :subject and d.status = :status",
        nativeQuery = true
    )
    fun findBySubject(
        @Param("subject") subject: String,
        @Param("user") userId: Long,
        @Param("status") status: Int
    ): List<UserInfo>?

    @Query(
        value = "select * from data d where d.user_id = :user and d.status = :status",
        nativeQuery = true
    )
    fun findReadyById(
        @Param("user") userId: Long,
        @Param("status") status: Int
    ): List<UserInfo>?

    @Query(
        value = "select * from data d where d.user_id = :user and d.status = :status and d.login = :login",
        nativeQuery = true
    )
    fun findReadyFileByName(
        @Param("user") userId: Long,
        @Param("status") status: Int,
        @Param("login") login: String,
    ): List<UserInfo>?

    @Query(
        value = "select * from data where status = :status",
        nativeQuery = true
    )
    fun search4InData(
        @Param("status") status: Int,
    ): List<UserInfo>?

    @Modifying
    @Query(
        "UPDATE data SET status = :status WHERE id = :id",
        nativeQuery = true
    )
    fun updateNewEv(
        @Param("id") id: Long,
        @Param("status") status: Int
    )

    @Modifying
    @Query(
        "UPDATE data SET status = :status, " +
                "subject = :subject, " +
                "login = :login, " +
                "password = :password  " +
                "WHERE id = :id",
        nativeQuery = true
    )
    fun updateOutEv(
        @Param("id") id: Long,
        @Param("subject") subject: String,
        @Param("login") login: String,
        @Param("password") password: String,
        @Param("status") status: Int
    )

//    fun findByStatus(status: Int): List<UserInfo>?
}