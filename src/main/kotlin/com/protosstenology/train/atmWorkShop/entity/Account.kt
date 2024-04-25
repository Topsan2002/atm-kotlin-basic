package com.protosstenology.train.atmWorkShop.entity

import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.util.Date

@Entity
@Table(name = "TBAccount")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "accountNo")
    var accountNo: String? = null,

    @Column(name = "firstName")
    var firstName: String? = null,

    @Column(name = "lastName")
    var lastName: String? = null,

    @Column(name = "address")
    var address: String? = null,

    @Column(name = "tel")
    var tel: String? = null,

    @Column(name = "birthday")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    @Temporal(TemporalType.DATE)
    var birthday: String? = null,

    @Column(name = "status")
    var status : String? = null,

    @Column(name = "religion")
    var  religion: String? = null,

    @Column(name = "color")
    var color: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "age")
    var age: Int? = null,

    @Column(name = "amount", scale = 10)
    var amount: BigDecimal? = null,


    @Column(name = "gender")
    var gender: String? = null,

    @Column(name = "username")
    var username: String? = null,

    @Column(name = "password")
    var password: String? = null,





) {

}