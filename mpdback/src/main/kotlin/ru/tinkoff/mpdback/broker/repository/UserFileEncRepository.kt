package ru.tinkoff.mpdback.broker.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.tinkoff.mpdback.broker.model.UserFileEnc
import ru.tinkoff.mpdback.broker.model.UserInfoEnc


interface UserFileEncRepository : JpaRepository<UserFileEnc, Long> {

//    @Query(
//        value = "select * from file_enc d where d.user_id = :user and d.subject = :subject and d.type = :type",
//        nativeQuery = true
//    )
//    fun findByIdAndSubject(
//        @Param("user") userId: Long,
//        @Param("subject") subject: String,
//        @Param("type") type: Int
//    ): List<UserInfoEnc>?
//
//    @Query(
//        value = "select * from file_enc d where d.user_id = :user and d.type = :type",
//        nativeQuery = true
//    )
//    fun findByUserId(
//        @Param("user") userId: Long,
//        @Param("type") type: Int
//    ): List<UserInfoEnc>?

}