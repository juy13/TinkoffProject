package ru.tinkoff.mpdback.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.tinkoff.mpdback.jwt.model.RegistrationUser
import ru.tinkoff.mpdback.jwt.repository.RegistrationRepository


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegistrationRepositoryTest {

    @Autowired
    private lateinit var registrationRepository: RegistrationRepository

    @BeforeEach
    fun init() {
        listOfRegistration.forEach {
            registrationRepository.save(it)
        }
    }

    @Test
    fun `get list of users`() {
        val data = registrationRepository.findAll()

        Assertions.assertEquals(listOfRegistration.size, data.size)
        for (i in 0 until data.size) {
            Assertions.assertEquals(listOfRegistration[i].email, data[i].email)
            Assertions.assertEquals(listOfRegistration[i].login, data[i].login)
            Assertions.assertEquals(listOfRegistration[i].password, data[i].password)
        }
    }

    @Test
    fun `find user`() {
        val data = registrationRepository.find("test1", "test1")

        if (data != null) {
            Assertions.assertEquals("test1", data.email)
            Assertions.assertEquals("test1", data.login)
            Assertions.assertEquals("test1", data.password)
        }
    }


    private val listOfRegistration = mutableListOf(
        RegistrationUser(
            0, "test1", "test1", "test1"),
        RegistrationUser(
            0, "test2", "test1", "test2"),
        RegistrationUser(
            0, "test3", "test2", "test3"),
        RegistrationUser(
            0, "test4", "test3", "test4"),
    )

}