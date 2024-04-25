package com.protosstenology.train.atmWorkShop.service

import com.protosstenology.train.atmWorkShop.entity.Account
import com.protosstenology.train.atmWorkShop.repository.AtmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import kotlin.math.floor
import kotlin.math.round
import kotlin.random.Random

@Service
class AtmService {
    @Autowired
    lateinit var repository: AtmRepository;

    fun createAccount(newData : Account) : Map<String, Any> {
        var msg = "";
        try {
            if (newData.age!! < 10){
                msg = "อายุน้อยกว่า 10 ปี"
                return mapOf("message" to msg, "status" to false)
            }else if (newData.amount!! < BigDecimal.valueOf(1000)){
                msg = "เงินฝากน้อยกว่า 1000"
                return mapOf("message" to msg, "status" to false)
            }else if (!checkPhone(newData.tel!!)){
                msg = "เบอร์โทรไม่ถูกต้อง"
                return mapOf("message" to msg, "status" to false)
            }
            else{
                val accountNo = String.format("%010d",Random.nextInt(1,999999999));
                newData.accountNo = accountNo
                newData.amount = newData.amount!!.setScale(2,RoundingMode.DOWN)

                newData.firstName = newData.firstName!!.trim()
                newData.lastName = newData.lastName!!.trim()
                newData.username = newData.username!!.trim()
                repository.save(newData);
                msg = "เพิ่มสำเร็จ"
                val  res = mapOf("accountNo" to accountNo, "firstName" to newData.firstName!!, "lastName" to newData.lastName!!)
                return mapOf("message" to msg, "status" to true, "data" to res )
            }
        }catch (e : Exception){
            msg = "กรอกข้อมูลผิดพลาด"
            return mapOf("message" to msg, "status" to false)
        }

    }

    fun getInfoAccount(accountNo: String) : Map<String, Any> {
        val data = repository.findAccountByAccountNo(accountNo)
        if(data != null){
//            val formattedPhoneNumber = data.tel!!.replaceFirst("(\\d{3})(\\d{3})(\\d{4})".toRegex(), "$1-$2-$3")
//            data.tel = formattedPhoneNumber
            if(data.gender == null) data.gender = ""
            val res = mapOf("accountNo" to accountNo,"firstName" to data.firstName!!, "lastName" to data.lastName!!, "gender" to data.gender!!, "tel" to data.tel!!, "amount" to formatAmount(data.amount!!))
            return  mapOf("status" to true, "data" to res )
        }else{
            return mapOf("message" to "account not found", "status" to false)
        }
    }

    fun getAmount(accountNo: String ) : Map<String, Any>{
        val data = repository.findAccountByAccountNo(accountNo)
        return if(data != null){
            mapOf("status" to true, "data" to mapOf("amount" to data.amount!!,   "accountNo" to data.accountNo!!))
        }else{
            mapOf("status" to false, "message" to "ไม่พบบัญชี")
        }
    }

    fun deposit(accountNo: String, amount: BigDecimal) : Map<String, Any>{
        val data = repository.findAccountByAccountNo(accountNo);
        if(amount <= BigDecimal.valueOf(0)){
            return  mapOf("status" to false, "messages" to "ยอดฝากมีค่า <= 0")
        }
        else if(data != null){
            val newAmount = amount.setScale(2, RoundingMode.DOWN) + data.amount!!
            data.amount = newAmount
            repository.save(data);
            return  mapOf("status" to true, "data" to mapOf( "accountNo" to accountNo, "amount" to  formatAmount(data.amount!!)))
        }else{
            return  mapOf("status" to false, "messages" to "account not found")
        }
    }

    fun withdraw(accountNo: String, amount: BigDecimal) : Map<String, Any>{
        val data = repository.findAccountByAccountNo(accountNo);
        if(data != null){
            if(amount <= BigDecimal.valueOf(0)){
                return  mapOf("status" to false, "messages" to "ยอดถอนมีค่าน้อยกว่าหรือเท่ากับ 0")
            }
            else if(data.amount!! < amount ){
                return  mapOf("status" to false, "messages" to "จำนวนเงินน้อยกว่ายอดงเงินที่มี")
            }else{
                val newAmount =  data.amount!! - amount.setScale(2, RoundingMode.DOWN)
                data.amount = newAmount
                repository.save(data);
                val res = mapOf("accountNo" to accountNo, "amount" to formatAmount( data.amount!!))
                return  mapOf("status" to true, "data" to res)
            }
        }else{
            return  mapOf("status" to false, "messages" to "account not found")

        }
    }

    fun deleteAccount(accountNo: String) : Map<String, Any>{
        val data = repository.findAccountByAccountNo(accountNo);
        return if (data != null){
            repository.deleteById(data.id!!)
            mapOf("status" to true, "messages" to "ลบสำเร็จ", "accountNo" to accountNo)
        }else{
            mapOf("status" to false, "messages" to "account not found")
        }
    }

    fun checkPhone(input: String): Boolean {
        val regex = Regex("\\d{10}")
        return regex.matches(input)
    }

    fun formatAmount(amount: BigDecimal): String {
        return DecimalFormat("###,###,###,###,###.00").format(amount)+" บาท";
    }

    fun  login(username: String, password: String): Map<String, Any>{
        val data = repository.selectLogin(username,password);
         return if(data != null){
             mapOf("status" to true, "data" to data, "message" to "ลงชื่อเข้าใช้สำเร็จ")
        }else{
            mapOf("status" to false, "message" to "ลงชื่อเข้าใช้ไม่สำเร็จ")
        }
    }


    fun  createUser(newData : Account) :Map<String, Any>{
        var msg = "";
        try {
            if (newData.age!! < 10){
                msg = "อายุน้อยกว่า 10 ปี"
                return mapOf("message" to msg, "status" to false)
            }
            else{
                val accountNo = String.format("%010d",Random.nextInt(1,999999999));
                newData.accountNo = accountNo
                newData.amount = newData.amount!!.setScale(2,RoundingMode.DOWN)
                println(newData.amount)
                newData.firstName = newData.firstName!!.trim()
                newData.lastName = newData.lastName!!.trim()
                newData.username = newData.username!!.trim()
                repository.save(newData);
                msg = "เพิ่มสำเร็จ"
                val  res = mapOf("accountNo" to accountNo, "firstName" to newData.firstName!!, "lastName" to newData.lastName!!)
                return mapOf("message" to msg, "status" to true, "data" to res )
            }
        }catch (e : Exception){
            msg = "กรอกข้อมูลผิดพลาด"
            return mapOf("message" to msg, "status" to false)
        }
    }

    fun  getUserALl() :Map<String, Any>{
        return  mapOf("status" to true, "data" to repository.findAll())
    }

    fun getUserById(userId: Long) : Map<String, Any>{
        val data = repository.findById(userId);
        return mapOf("status" to true, "data" to data)
    }
    fun deleteUserById(id:Long) : Map<String, Any>{
        val data = repository.findAccountById(id);
        return if(data != null){
            repository.delete(data)
            mapOf("status" to true, "messages" to "ลบสำเร็จ", "data" to data)
        }else{
            mapOf("status" to false, "messages" to "account not found")
        }

    }

    fun updateUserById(account: Account) : Map<String, Any>{

        val data = repository.findAccountById(account.id!!);
        if(data != null){

            data.firstName = account.firstName!!.trim()
            data.lastName = account.lastName!!.trim()
            data.username = account.username!!.trim()
            data.password = account.password!!.trim()
            data.email = account.email!!.trim()
            data.tel  = account.tel!!
            data.address = account.address!!
            data.birthday = account.birthday!!
            data.status = account.status!!
            data.religion = account.religion!!
            data.color = account.color!!

            repository.saveAndFlush(data)

            return mapOf("status" to false, "messages" to "แก้ไขสำเร็จ", "data" to data)

        }
        return mapOf("status" to false, "messages" to "แก้ไขไม่สำเร็จ")
    }


}