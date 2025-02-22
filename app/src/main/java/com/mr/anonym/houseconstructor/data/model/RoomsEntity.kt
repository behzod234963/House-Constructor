package com.mr.anonym.houseconstructor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("rooms")
data class RoomsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val parentID:Int,
    val roomName:String,
    val length:Double,
    val width:Double,
    val height:Double,
    val cement:Double,
    val cementWithBag:Int,
    val sand:Double,
    val crushedStone:Double,
    val brick:Int,
    val cementCost:Double,
    val brickCost: Double
)
