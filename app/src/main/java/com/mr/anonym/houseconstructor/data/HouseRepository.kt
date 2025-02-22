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
    fun getAllHouse() = dao.getAllHouse()
    fun getHouse(id:Int) = dao.getHouse(id)
    suspend fun insertRoom(room:RoomsEntity){
        dao.insertRoom(room)
    }
    suspend fun updateCementCost(id:Int,cementCost:Double){
        dao.updateCementCost(id, cementCost)
    }
    suspend fun updateBrickCost(id:Int,brickCost:Double){
        dao.updateBrickCost(id, brickCost)
    }
    suspend fun deleteRoom(room: RoomsEntity){ dao.deleteRoom(room) }
    suspend fun deleteRoomByParentID(parentID: Int){
        dao.deleteRoomByParentID(parentID)
    }
    suspend fun deleteRooms(rooms:List<RoomsEntity>){
        dao.deleteRooms(rooms)
    }
    fun getRoomsByParentID(parentID:Int) = dao.getRoomsByParentID(parentID)
}