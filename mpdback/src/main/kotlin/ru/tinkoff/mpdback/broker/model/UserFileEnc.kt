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
@Table(name = "file_enc")
data class UserFileEnc(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "userId", nullable = false, unique = false)
    val userId: Long = 0,

    @Column(name = "file", nullable = true, unique = true)
    val file: String = "unknown",

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

        private fun encrypt(strToEncrypt: ByteArray, secret: String): ByteArray {
            try {
                val secretKey = setKey(secret)
                val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                return cipher.doFinal(strToEncrypt)
            } catch (e: Exception) {

                println("MPDError while encrypting: $e")
            }
            return byteArrayOf()
        }

        private fun decrypt(strToDecrypt: ByteArray, secret: String): ByteArray {
            try {
                val secretKey = setKey(secret)
                val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, secretKey)
                return cipher.doFinal(strToDecrypt)
            } catch (e: Exception) {
                println("Error while decrypting: $e")
            }
            return byteArrayOf()
        }

        @Throws(Exception::class)
        fun readFile(filePath: String): ByteArray {
            val file = File(filePath)
            val fileContents = file.readBytes()
            val inputBuffer = BufferedInputStream(
                FileInputStream(file)
            )

            inputBuffer.read(fileContents)
            inputBuffer.close()

            return fileContents
        }

        @Throws(Exception::class)
        fun saveFile(fileData: ByteArray, path: String) {
            val file = File(path)
            val bos = BufferedOutputStream(FileOutputStream(file, false))
            bos.write(fileData)
            bos.flush()
            bos.close()
        }

        fun encryptFile(pathIn: String, pathOut: String, key: String) {
            try {
                val fileData = readFile(pathIn)
                saveFile(encrypt(fileData, key), pathOut)
            } catch (e: Exception) {
                println("Huston it's ERROR!!!")
            }
        }

        fun decryptFile(pathIn: String, pathOut: String, key: String) {
            try {
                val fileData = readFile(pathIn)
                saveFile(decrypt(fileData, key), pathOut)
            } catch (e: Exception) {
                println("Huston it's ERROR!!!")
            }
        }

    }
}