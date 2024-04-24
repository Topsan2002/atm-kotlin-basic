package com.protosstenology.train.atmWorkShop.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "TBAccount")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "accountNo")
    var accountNo: String? = null,

    @Column(name = "gender")
    var gender: String? = null,

    @Column(name = "firstName")
    var firstName: String? = null,

    @Column(name = "lastName")
    var lastName: String? = null,

    @Column(name = "age")
    var age: Int? = null,

    @Column(name = "tel")
    var tel: String? = null,

    @Column(name = "amount", scale = 10)
    var amount: BigDecimal? = null,



) {

}