package ru.tinkoff.mpdback.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.tinkoff.mpdback.enums.Status
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfoLite
import ru.tinkoff.mpdback.mpdbackApi.repository.UserInfoRepository


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserInfoRepositoryTest {

    @Autowired
    private lateinit var userInfoRepository: UserInfoRepository

    @BeforeEach
    fun init() {
        listOfUsersData.forEach {
            userInfoRepository.save(it)
        }
    }

    @Test
    fun `get list of users date`() {
        val data = userInfoRepository.findAll()

        val dataIn = listOfUsersData.map {
            UserInfo.cast2UserInfoLite(it)
        }
        val dataOut = data.map {
            UserInfo.cast2UserInfoLite(it)
        }

        assertEquals(dataIn, dataOut)
    }

    @Test
    fun `find by subject in users date`() {
        val data = userInfoRepository.findBySubject("test", 1, Status.OUT_DATA.status)

        val dataIn = listOf(UserInfoLite("test", "test2", "test2"))

        val dataOut = data?.map {
            UserInfo.cast2UserInfoLite(it)
        }

        assertEquals(dataIn, dataOut)
    }

    @Test
    fun `find by id in users date`() {
        val data = userInfoRepository.findReadyById(1, Status.OUT_DATA.status)

        val dataIn = listOf(UserInfoLite("test", "test2", "test2"))

        val dataOut = data?.map {
            UserInfo.cast2UserInfoLite(it)
        }

        assertEquals(dataIn, dataOut)
    }

    @Test
    fun `find file by id in users date`() {
        val data = userInfoRepository.findReadyFileByName(1, Status.FILE_OUT.status, "log.txt")

        val dataIn = listOf(UserInfoLite("", "log.txt", ""))

        val dataOut = data?.map {
            UserInfo.cast2UserInfoLite(it)
        }

        assertEquals(dataIn, dataOut)
    }

    @Test
    fun `find data by status in users date`() {
        val data = userInfoRepository.search4InData(Status.ERROR.status)

        val dataIn = listOfUsersData.filter { it.status == Status.ERROR }.map { UserInfo.cast2UserInfoLite(it) }

        val dataOut = data?.map {
            UserInfo.cast2UserInfoLite(it)
        }

        assertEquals(dataIn, dataOut)
    }

    @Test
    fun `update status in users date`() {
        val id = userInfoRepository.save(UserInfo(0, 1, "test1", "test", "Test1", Status.IN_DATA)).id

        userInfoRepository.updateNewEv(id, Status.OUT_DATA.status)
        val data = userInfoRepository.findById(id)

        val dataIn = UserInfoLite("test1", "test", "Test1")

        val dataOut = UserInfo.cast2UserInfoLite(data.get())

        assertEquals(dataIn, dataOut)
    }

    private val listOfUsersData = mutableListOf(
        UserInfo(
            1, 1, "test1", "test1", "test1", Status.IN_DATA
        ),
        UserInfo(
            2, 2, "", "test2", "test2", Status.OUT_DATA
        ),
        UserInfo(
            3, 3, "test3", "", "", Status.ERROR
        ),
        UserInfo(
            4, 4, "test3", "", "", Status.ERROR
        ),
        UserInfo(
            5, 1, "test3", "", "", Status.ERROR
        ),
        UserInfo(
            6, 1, "test", "test2", "test2", Status.OUT_DATA
        ),
        UserInfo(
            7, 1, "", "log.txt", "", Status.FILE_OUT
        )
    )


}