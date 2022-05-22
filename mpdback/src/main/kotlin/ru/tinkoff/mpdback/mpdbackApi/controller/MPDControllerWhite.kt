package ru.tinkoff.mpdback.mpdbackApi.controller

import org.springframework.web.bind.annotation.*
import ru.tinkoff.mpdback.mpdbackApi.model.UserInfo
import ru.tinkoff.mpdback.mpdbackApi.service.MPDService

@RestController
@RequestMapping("/white-api")
class MPDControllerWhite(private val mpdService: MPDService) {

    @PostMapping("/add-record")
    fun addRecord(@RequestBody userInfo: UserInfo) {
        mpdService.addRecord(userInfo)
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