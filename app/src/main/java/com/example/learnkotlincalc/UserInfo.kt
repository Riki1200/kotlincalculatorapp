package com.example.learnkotlincalc





class Admin(name: String) {
    var names: String = name
    init {
        val u= UserInfo(name,null,null)
        u.age = 19
        u.adress = "Puerto Rico"
        names = ""
        println("$u  $names")
    }
}

data class UserInfo(var name:String?,  var age: Int?, var adress: String?)