package com.mr.anonym.houseconstructor.data

import androidx.room.Query
import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.data.model.RoomsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HouseRepository @Inject constructor(private val dao: ConstructorDao) {

    suspend fun insertHome(house: HouseEntity){
        dao.insertHouse(house)
    }

    suspend fun deleteHome(home:HouseEntity){
        dao.deleteHome(home)
    }

    suspend fun updateTotalCement(id:Int,totalCement:Double){
        dao.updateTotalCement(id, totalCement)
    }
    suspend fun updateTotalBrick(id:Int, totalBrick:Double){
        dao.updateTotalBrick(id, totalBrick)
    }
    suspend fun updateTotalRooms(id:Int, totalRooms:Int){
        dao.updateTotalRooms(id, totalRooms)
    }
    fun getAllHouse() = dao.getAllHouse()
    fun getHouse(id:Int) = dao.getHouse(id)
    suspend fun insertRoom(room:RoomsEntity){
        dao.insertRoom(room)
    }
    fun getRooms() = dao.getRooms()
    suspend fun deleteRoom(room: RoomsEntity){ dao.deleteRoom(room) }
    suspend fun deleteRooms(rooms:List<RoomsEntity>){
        dao.deleteRooms(rooms)
    }
    fun getRoomsByParentID(parentID:Int) = dao.getRoomsByParentID(parentID)
    fun getRoom(id: Int) = dao.getRoom(id)
}