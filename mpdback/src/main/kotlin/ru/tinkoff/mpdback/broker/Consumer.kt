package ru.tinkoff.mpdback.broker

import com.google.gson.Gson
import org.springframework.stereotype.Component
import ru.tinkoff.mpdback.broker.repository.UserInfoEncRepository
import ru.tinkoff.mpdback.broker.service.DataEncryptionService
import ru.tinkoff.mpdback.broker.service.FileEncryptionService
import ru.tinkoff.mpdback.enums.Status
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.repository.UserInfoRepository
import javax.jms.JMSException
import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.TextMessage

@Component
class Consumer(
    private val userInfoRepository: UserInfoRepository,
    private val userInfoEncRepository: UserInfoEncRepository,
    private val dataEncryptionService: DataEncryptionService,
    private val fileEncryptionService: FileEncryptionService
) : MessageListener {

    private val gson = Gson()

    override fun onMessage(message: Message) {
        if (message is TextMessage) {
            try {
                val data = gson.fromJson(message.text, UserInfo::class.java)
                when (data.status) {
                    Status.IN_DATA -> {
                        println("Data enc")
                        dataEncryptionService.newData(data)
                        userInfoRepository.deleteById(data.id)
                    }
                    Status.OUT_DATA_PREPARE -> {
                        println("Data dec")
                        val userData = dataEncryptionService.outData(data)
                        userData.forEach {
                            userInfoRepository.save(UserInfo(
                                0,
                                it.userId,
                                it.subject,
                                it.login,
                                it.password,
                                Status.OUT_DATA
                            ))
                        }
                        userInfoRepository.deleteById(data.id)
                    }
                    Status.FILE_IN -> {
                        println("File enc")
                        fileEncryptionService.newFile(data)
                        userInfoRepository.deleteById(data.id)
                    }
                    Status.FILE_OUT_PREPARE -> {
                        println("File dec")
                        fileEncryptionService.outFile(data)
                        userInfoRepository.updateNewEv(data.id, Status.FILE_OUT.status)
                    }
                    else -> {
                        println("Undefined service")
                    }
                }
                println("Message has been consumed : $data")
            } catch (ex: JMSException) {
                throw RuntimeException(ex)
            }
        } else {
            throw IllegalArgumentException("Message must be of type TextMessage")
        }
    }
}