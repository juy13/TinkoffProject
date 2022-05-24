package ru.tinkoff.mpdback.broker.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.tinkoff.mpdback.broker.model.UserInfoEnc


interface UserInfoEncRepository : JpaRepository<UserInfoEnc, Long> {

    @Query(
        value = "select * from data_enc d where d.user_id = :user and d.subject = :subject",
        nativeQuery = true
    )
    fun findByIdAndSubject(
        @Param("user") userId: Long,
        @Param("subject") subject: String
    ): List<UserInfoEnc>?

    @Query(
        value = "select * from data_enc d where d.user_id = :user",
        nativeQuery = true
    )
    fun findByUserId(
        @Param("user") userId: Long
    ): List<UserInfoEnc>?

}