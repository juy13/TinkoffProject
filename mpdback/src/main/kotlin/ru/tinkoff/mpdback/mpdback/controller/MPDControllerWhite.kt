package ru.tinkoff.mpdback.mpdback.controller

import org.springframework.web.bind.annotation.*
import ru.tinkoff.mpdback.mpdback.model.UserInfo
import ru.tinkoff.mpdback.mpdback.service.MPDService

@RestController
@RequestMapping("/white-api")
class MPDControllerWhite(private val mpdService: MPDService) {

    @PostMapping("/add-record")
    fun addRecord(@RequestBody userInfo: UserInfo) {

    }


//    @GetMapping("/userId")
//    fun getUser(@RequestParam(defaultValue = "admin") login: String) =
//        mpdService.getUser(login)
//
//    @PostMapping("/newUser")
//    fun registrationUser(@RequestBody registrationUser: RegistrationUser) {
//        mpdService.registrationUser(registrationUser)
//    }
//
//    @GetMapping("/getAllGoods")
//    fun getAllGoods() : String {
//        return "Hello"
//    }

}