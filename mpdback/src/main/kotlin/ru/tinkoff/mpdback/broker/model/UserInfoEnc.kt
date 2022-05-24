package ru.tinkoff.mpdback.broker.model


import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import ru.tinkoff.mpdback.enums.Status
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.persistence.*


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_enc")
data class UserInfoEnc(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "userId", nullable = false, unique = false)
    val userId: Long = 0,

    @Column(name = "subject", nullable = true, unique = false)
    val subject: String = "unknown",

    @Column(name = "login", nullable = true, unique = false)
    val login: String = "unknown",

    @Column(name = "password", nullable = true, unique = false)
    val password: String = "unknown"
) {
    companion object {

        private fun setKey(myKey: String): SecretKeySpec? {
            try {
                var key: ByteArray = myKey.toByteArray(charset("UTF-8"))
                val sha = MessageDigest.getInstance("SHA-1")
                key = sha.digest(key)
                key = key.copyOf(16)
                return SecretKeySpec(key, "AES")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            return null
        }

        private fun encrypt(strToEncrypt: String, secret: String): String {
            try {
                val secretKey = setKey(secret)
                val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                return Base64.getEncoder().encodeToString(
                    cipher.doFinal
                        (strToEncrypt.toByteArray(charset("UTF-8")))
                )
            } catch (e: Exception) {

                println("MPDError while encrypting: $e")
            }
            return ""
        }

        private fun decrypt(strToDecrypt: String?, secret: String): String {
            try {
                val secretKey = setKey(secret)
                val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, secretKey)
                return String(
                    cipher.doFinal(
                        Base64.getDecoder().decode(strToDecrypt)
                    )
                )
            } catch (e: Exception) {
                println("Error while decrypting: $e")
            }
            return ""
        }

        fun cast2UserInfoEnc(userInfo: UserInfo, key: String): UserInfoEnc {
            return UserInfoEnc(
                0, userInfo.userId, encrypt(userInfo.subject, key),
                encrypt(userInfo.login, key), encrypt(userInfo.password, key)
            )
        }

        fun cast2UserInfo(userInfoEnc: UserInfoEnc, key: String): UserInfo {
            return UserInfo(
                0, userInfoEnc.userId, decrypt(userInfoEnc.subject, key),
                decrypt(userInfoEnc.login, key), decrypt(userInfoEnc.password, key), Status.OUT_DATA
            )
        }

        fun encryptSubject(subject: String, key: String): String {
            return encrypt(subject, key)
        }

    }
}