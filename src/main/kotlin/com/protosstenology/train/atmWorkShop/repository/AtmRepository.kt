package com.protosstenology.train.atmWorkShop.repository

import com.protosstenology.train.atmWorkShop.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AtmRepository : JpaRepository<Account, Long>  {
    fun findAccountByAccountNo(accountNo: String): Account?
    @Query("select a from Account a where a.username = :username and a.password = :password  ")
    fun selectLogin(@Param("username") username: String, @Param("password") password : String): Account?
//    fun findAccountByUsernameAndPassword(username: String, password:String): Account?
    fun  findAccountById(@Param("id") id: Long): Account?
}