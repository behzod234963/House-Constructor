package com.mr.anonym.houseconstructor.helpers

sealed class AddHouseEvent {
    data class ChangeHomeName(val homeName:String) :AddHouseEvent()
    data class ChangeRoomName(val roomName:String):AddHouseEvent()
    data class ChangeLength(val length:String):AddHouseEvent()
    data class ChangeWidth(val width:String):AddHouseEvent()
    data class ChangeHeight(val height:String):AddHouseEvent()
}