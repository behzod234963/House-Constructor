package com.mr.anonym.houseconstructor.helpers

import kotlin.math.pow
import kotlin.math.round

fun Double.roundTo(n: Int): Double {
    val factor = 10.0.pow(n)
    return round(this * factor) / factor
}

fun Char.charChecker(): Boolean {
    return if (
        this.code in 32..45
        || this.code == 47
        || this.code in 58..127
    ){
        false
    }else{
        true
    }
}

fun String.stringChecker():Boolean{
    val regexPattern = Regex("^[0-9]+\\.?[0-9]*$")
    return if (regexPattern.matches(this)){
        true
    }else{
        false
    }
}