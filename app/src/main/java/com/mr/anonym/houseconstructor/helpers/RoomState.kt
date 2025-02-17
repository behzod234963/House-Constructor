package com.mr.anonym.houseconstructor.helpers

import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.data.model.RoomsEntity

data class RoomState(
    val homeModel:HouseEntity? = null,
    val houses:List<HouseEntity> = emptyList(),
    val rooms:List<RoomsEntity> = emptyList()
)