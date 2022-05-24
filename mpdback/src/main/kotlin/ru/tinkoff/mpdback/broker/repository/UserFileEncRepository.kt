package ru.tinkoff.mpdback.broker.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.tinkoff.mpdback.broker.model.UserFileEnc
import ru.tinkoff.mpdback.broker.model.UserInfoEnc


interface UserFileEncRepository : JpaRepository<UserFileEnc, Long> {

}