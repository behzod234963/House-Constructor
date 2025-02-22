package com.mr.anonym.houseconstructor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("house")
data class HouseEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val parentID:Int,
    val houseName:String,
    val date:String,
    val totalRooms:Int,
    val totalCement:Double,
    val totalCementWithBag:Int,
    val totalSand:Double,
    val totalCrushedStone:Double,
    val totalBrick:Int,
    val totalCost:Double
)