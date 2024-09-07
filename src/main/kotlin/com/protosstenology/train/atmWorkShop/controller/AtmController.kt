package com.protosstenology.train.atmWorkShop.controller

import com.protosstenology.train.atmWorkShop.entity.Account
import com.protosstenology.train.atmWorkShop.service.AtmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.AccessType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@CrossOrigin(origins = ["*"])
class AtmController {
    @Autowired
    lateinit var atmService: AtmService
//    @GetMapping
//    fun getAtm()

    @PostMapping("/createAcount")
    fun createAccou(@RequestBody newAccount : Account) : ResponseEntity<Map<String, Any>>{

        return  ResponseEntity.ok().body(atmService.createAccount(newAccount))
    }

    @PostMapping("/getAccount")
    fun getAccount(@RequestParam("accountNo") accountNo : String) : ResponseEntity<Map<String, Any>>{
        return ResponseEntity.ok().body(atmService.getInfoAccount(accountNo));
    }

    @GetMapping("/getAmount")
    fun getAmount(@RequestParam("accountNo") accountNo : String) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.getAmount(accountNo))

    }

    @PutMapping("/deposit")
    fun depositAccount(@RequestParam("accountNo") accountNo : String, @RequestParam("amount") amount : BigDecimal) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.deposit(accountNo.trim(), amount));
    }

    @PutMapping("/withdraw")
    fun  deleteAccount(@RequestParam("accountNo") accountNo : String, @RequestParam("amount") amount: BigDecimal) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.withdraw(accountNo.trim(), amount));
    }

    @GetMapping("/removeAccount")
    fun removeAccount(@RequestParam("accountNo") accountNo : String) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.deleteAccount(accountNo));
    }


   @PostMapping("/login")
   fun  login(@RequestBody data : Account) : ResponseEntity<Map<String, Any>>{
//       print(data)
       return  ResponseEntity.ok().body(atmService.login(data.username!!, data.password!!))
   }

    @PostMapping("/createUser")
    fun createUser(@RequestBody newUser : Account) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.createUser(newUser))
    }

    @GetMapping("/getUsers")
    fun getUser() : ResponseEntity<Map<String, Any>>{
        return ResponseEntity.ok().body(atmService.getUserALl())
    }

    @GetMapping("getUserById")
    fun getUserById(@RequestParam("id") id : Long) : ResponseEntity<Map<String, Any>>{
        return ResponseEntity.ok().body(atmService.getUserById(id))

    }

    @PutMapping("/updateUser")
    fun  updateUser(@RequestBody data : Account, @RequestParam("id") id : Long) : ResponseEntity<Map<String, Any>>{
        data.id = id
        return  ResponseEntity.ok().body(atmService.updateUserById(data))
    }

    @DeleteMapping("/removeUser")
    fun  removeUser(@RequestParam("id") id : Long) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.deleteUserById(id))
    }




}