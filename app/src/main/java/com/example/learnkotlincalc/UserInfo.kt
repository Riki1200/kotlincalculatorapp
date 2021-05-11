package com.example.learnkotlincalc

class Admin(name: String) {
    var names: String = name
    init {
        val u= UserInfo(name,null,null)
        u.age = 19
        u.adress = "Puerto Rico"
        println("$u at $names")
    }
}

data class UserInfo(var name:String?,  var age: Int?, var adress: String?)