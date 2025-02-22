package com.mr.anonym.houseconstructor.helpers

class CalculateCost(
    private val cementWithBag: Int,
    private val brick: Int
) {
    fun cementCost(cementCost: Double): Double {
        return if (cementCost != 0.0){
            cementCost * cementWithBag
        }else{
            0.0
        }
    }
    fun brickCost(brickCost:Double):Double{
        return if (brickCost != 0.0){
            brickCost * brick
        }else{
            0.0
        }
    }
}