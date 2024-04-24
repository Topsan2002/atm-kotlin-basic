package com.protosstenology.train.atmWorkShop.repository

import com.protosstenology.train.atmWorkShop.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AtmRepository : JpaRepository<Account, Long>  {
    fun findAccountByAccountNo(accountNo: String): Account?
}