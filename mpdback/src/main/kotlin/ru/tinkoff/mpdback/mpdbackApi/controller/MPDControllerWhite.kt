package ru.tinkoff.mpdback.mpdbackApi.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.tinkoff.mpdback.enums.FileRc
import ru.tinkoff.mpdback.error.FileDownload
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.service.MPDService
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


@RestController
@RequestMapping("/white-api")
class MPDControllerWhite(
    private val mpdService: MPDService,
) {

    @PostMapping("/add-record")
    fun addRecord(
        @RequestHeader("Authorization") token: String,
        @RequestBody userInfo: UserInfo
    ): ResponseEntity<*>? {
        val ans = mpdService.addRecord(token, userInfo)
        return ResponseEntity(ans, HttpStatus.OK)
    }

    @GetMapping("/prepare-data-by-subject")
    fun prepareBySubject(
        @RequestHeader("Authorization") token: String,
        @RequestParam subject: String
    ): ResponseEntity<*> {
        val ans = mpdService.prepareBySubject(token, subject)
        return ResponseEntity(ans, HttpStatus.OK)
    }

    @GetMapping("/prepare-data-all")
    fun prepareAll(
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val ans = mpdService.prepareAll(token)
        return ResponseEntity(ans, HttpStatus.OK)
    }

    @GetMapping("/get-all-data")
    fun getAll(
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val ans = mpdService.getAll(token)
        return ResponseEntity(ans, HttpStatus.OK)
    }

    @RequestMapping(
        value = ["/upload"],
        method = [RequestMethod.POST],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    @Throws(
        IOException::class
    )
    fun fileUpload(
        @RequestParam("file") file: MultipartFile,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val ans = mpdService.uploadFile(token, file)
        return ResponseEntity(ans, HttpStatus.OK)
    }

    @GetMapping("/prepare-file-by-name")
    fun prepareFileByName(
        @RequestHeader("Authorization") token: String,
        @RequestParam("name") name: String
    ): ResponseEntity<*> {
        val ans = mpdService.prepareFileByName(token, name)
        return ResponseEntity(ans, HttpStatus.OK)
    }

    @RequestMapping(value = ["/download"], method = [RequestMethod.GET])
    @Throws(IOException::class)
    fun downloadFile(
        @RequestHeader("Authorization") token: String,
        @RequestParam("name") name: String
    ): ResponseEntity<*>? {
        return when (val ans = mpdService.downLoadFile(token, name)) {
            is FileDownload -> {
                val fileDownload = ans as FileDownload
                if(fileDownload.rc == FileRc.FileError) {
                    ResponseEntity(fileDownload, HttpStatus.OK)
                } else {
                    fileDownload.response
                }
            }
            else -> {
                ResponseEntity(ans, HttpStatus.OK)
            }
        }
    }


}