package com.mr.anonym.houseconstructor.helpers

import android.icu.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.round

fun Double.roundTo(n: Int): Double {
    val factor = 10.0.pow(n)
    return round(this * factor) / factor
}
fun String.moneySeparator(cost:Double):String{
    val factory = DecimalFormat("#,###")
    return factory.format(cost)
}
fun Double.moneyType():String = this.toString().moneySeparator(this)

fun String.brickSeparator(brick:Int):String{
    val factory = DecimalFormat("#,###")
    return factory.format(brick)
}
fun Int.brickType():String = this.toString().brickSeparator(this)

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
    val regexPattern = Regex("^[0-9]+\\.?[0-9]*$") /* Regex pattern for numbers and dot */
    return if (regexPattern.matches(this)){
        true
    }else{
        false
    }
}