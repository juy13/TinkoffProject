package ru.tinkoff.mpdback.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.tinkoff.mpdback.broker.model.UserInfoEnc
import ru.tinkoff.mpdback.broker.repository.UserInfoEncRepository


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserInfoEncRepositoryTest {

    @Autowired
    private lateinit var userInfoEncRepository: UserInfoEncRepository

    @BeforeEach
    fun init() {
        listOfUsersData.forEach {
            userInfoEncRepository.save(it)
        }
    }

    @Test
    fun `get list of users date`() {
        val data = userInfoEncRepository.findAll()

        Assertions.assertEquals(listOfUsersData.size, data.size)
        for (i in 0 until data.size) {
            Assertions.assertEquals(listOfUsersData[i].userId, data[i].userId)
            Assertions.assertEquals(listOfUsersData[i].subject, data[i].subject)
            Assertions.assertEquals(listOfUsersData[i].login, data[i].login)
            Assertions.assertEquals(listOfUsersData[i].password, data[i].password)
        }
    }

    @Test
    fun `find by subject and id`() {
        val data = userInfoEncRepository.findByIdAndSubject(1, "test1")
        val userData = data?.get(0)

        if (userData != null) {
            Assertions.assertEquals(1, userData.userId)
            Assertions.assertEquals("test1", userData.subject)
            Assertions.assertEquals("test1", userData.login)
            Assertions.assertEquals("test1", userData.password)
        }
    }

    @Test
    fun `find by user id`() {
        val data = userInfoEncRepository.findByUserId(1)

        if (data != null) {
            Assertions.assertEquals(4, data.size)
        }
    }


    private val listOfUsersData = mutableListOf(
        UserInfoEnc(
            1, 1, "test1", "test1", "test1"),
        UserInfoEnc(
            2, 2, "", "test2", "test2"),
        UserInfoEnc(
            3, 3, "test3", "", ""),
        UserInfoEnc(
            4, 4, "test3", "", ""),
        UserInfoEnc(
            5, 1, "test3", "", ""),
        UserInfoEnc(
            6, 1, "test", "test2", "test2"),
        UserInfoEnc(
            7, 1, "", "log.txt", "")
    )

}