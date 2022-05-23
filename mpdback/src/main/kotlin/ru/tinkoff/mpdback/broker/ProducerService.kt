package ru.tinkoff.mpdback.broker

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.core.JmsTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import ru.tinkoff.mpdback.enums.Status
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.repository.UserInfoRepository

@Component
class ProducerService() {

    @Autowired
    private val userInfoRepository: UserInfoRepository? = null

    @Autowired
    private val jmsTemplate: JmsTemplate? = null

    @Scheduled(cron = "*/5 * * * * ?")
    fun produceIn() {
//        println("Produce")
        val newEvents = userInfoRepository?.search4InData(Status.IN_DATA.status)
        newEvents?.forEach {
            if (it.status != Status.ERROR) {
                userInfoRepository?.updateNewEv(it.id, Status.DATA_PROCESS.status)
                sendMessageToDestination(it)
            }
        }
//        println("Produce")
    }

    @Scheduled(cron = "*/5 * * * * ?")
    fun produceOut() {
//        println("Produce")
        val newEvents = userInfoRepository?.search4InData(Status.OUT_DATA_PREPARE.status)
        newEvents?.forEach {
            if (it.status != Status.ERROR) {
                userInfoRepository?.updateNewEv(it.id, Status.DATA_PROCESS.status)
                sendMessageToDestination(it)
            }
        }
//        println("Produce")
    }

//    @Scheduled(cron = "*/5 * * * * ?")
//    fun produceNewFile() {
//        val newEvents = userInfoRepository?.search4InData(Status.FILE_IN.status)
//        newEvents?.forEach {
//            if (it.status != Status.ERROR) {
//                userInfoRepository?.updateNewEv(it.id, Status.FILE_PROGRESS.status)
//                sendMessageToDestination(it)
//            }
//        }
//    }

    fun sendMessageToDestination(message: UserInfo) {
        val gson = Gson()
        val jsonString = gson.toJson(message)
        jmsTemplate!!.convertAndSend(jsonString)
    }

}