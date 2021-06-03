package com.example.learnkotlincalc.helper

import net.objecthunter.exp4j.operator.Operator


open  class DivisionOperator : Operator("÷",1,true, PRECEDENCE_DIVISION) {
    companion object {

    }
    override fun apply(vararg args: Double): Double {
        TODO("Not yet implemented")
    }
}

open class Multiplixy : Operator("×",1,true, PRECEDENCE_MULTIPLICATION) {
    override fun apply(vararg args: Double): Double {
        val posZero = args[0].toInt()
        if (posZero.toDouble() != args[0]){
            throw Exception("Not same, to error")
        }
        TODO("Not yet implemented")
    }
}
