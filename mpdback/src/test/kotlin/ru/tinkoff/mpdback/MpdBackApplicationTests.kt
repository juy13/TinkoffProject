//package ru.tinkoff.mpdback
//
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import ru.tinkoff.mpdback.repositories.configuration.repositoryInterfaces.JpaRecordsRepo
//import ru.tinkoff.mpdback.repositories.repositoryImpl.JpaRecordsRepositoryImpl
//import ru.tinkoff.mpdback.repositories.repositoryImpl.JpaUserRepositoryImpl
//import ru.tinkoff.mpdback.repositories.configuration.repositoryInterfaces.JpaUserRepo
////import ru.tinkoff.mpdback.whiteApi.MPDController
//import ru.tinkoff.mpdback.user.service.JWTService
//import javax.crypto.Cipher
//import java.io.UnsupportedEncodingException
//import java.security.NoSuchAlgorithmException
//import javax.crypto.spec.SecretKeySpec
//import java.security.MessageDigest
//import java.util.*
//
//@DataJpaTest
//class MpdBackApplicationTests {
//
//	@Autowired
//	private lateinit var userRepo: JpaUserRepo
//	@Autowired
//	private lateinit var recordsRepo: JpaRecordsRepo
//
//
//	// key
//	val password = "secretkey"
//	private var secretKey: SecretKeySpec? = null
//	private lateinit var key: ByteArray
//
//	// set Key
//	fun setKey(myKey: String) {
////		var sha: MessageDigest? = null
//		try {
//			key = myKey.toByteArray(charset("UTF-8"))
//			val sha = MessageDigest.getInstance("SHA-1")
//			key = sha.digest(key)
//			key = key.copyOf(16)
//			secretKey = SecretKeySpec(key, "AES")
//		} catch (e: NoSuchAlgorithmException) {
//			e.printStackTrace()
//		} catch (e: UnsupportedEncodingException) {
//			e.printStackTrace()
//		}
//	}
//
//	// method to encrypt the secret text using key
//	fun encrypt(strToEncrypt: String, secret: String): String? {
//		try {
//			setKey(secret)
//			val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
//			cipher.init(Cipher.ENCRYPT_MODE, secretKey)
//			return Base64.getEncoder().encodeToString(cipher.doFinal
//				(strToEncrypt.toByteArray(charset("UTF-8"))))
//		} catch (e: Exception) {
//
//			println("MPDError while encrypting: $e")
//		}
//		return null
//	}
//
////	@Test
////	fun contextLoads() {
////	}
//
//	@Test
//	fun `Test new user`() {
//		val login = "User"
//		val loginEnc = "iNC"//encrypt(login, password)
//
//		val jpaRecordsRepositoryImpl = JpaRecordsRepositoryImpl(recordsRepo)
//		val jpaUsersRepositoryImpl = JpaUserRepositoryImpl(userRepo)
//		val mpdService = JWTService(jpaRecordsRepositoryImpl, jpaUsersRepositoryImpl)
//		val mpdController = MPDController(mpdService)
//
//		val user = loginEnc.let { mpdController.getUser(it) }
//
//		println(user)
//	}
//
//}
