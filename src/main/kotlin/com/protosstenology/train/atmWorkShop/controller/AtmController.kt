package com.protosstenology.train.atmWorkShop.controller

import com.protosstenology.train.atmWorkShop.entity.Account
import com.protosstenology.train.atmWorkShop.service.AtmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class AtmController {
    @Autowired
    lateinit var atmService: AtmService
//    @GetMapping
//    fun getAtm()

    @PostMapping("/createAcount")
    fun createAccount(@RequestBody newAccount : Account) : ResponseEntity<Map<String, Any>>{

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

    @PostMapping("/removeAccount")
    fun removeAccount(@RequestParam("accountNo") accountNo : String) : ResponseEntity<Map<String, Any>>{
        return  ResponseEntity.ok().body(atmService.deleteAccount(accountNo));
    }

}