package com.mr.anonym.houseconstructor.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.data.model.RoomsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConstructorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouse(house: HouseEntity)

    @Delete
    suspend fun deleteHome(home:HouseEntity)

    @Query("UPDATE house SET totalCement=:totalCement WHERE id=:id")
    suspend fun updateTotalCement(id: Int, totalCement: Double)

    @Query("UPDATE house SET totalBrick=:totalBrick WHERE id=:id")
    suspend fun updateTotalBrick(id: Int, totalBrick: Double)

    @Query("UPDATE house SET totalRooms=:totalRooms WHERE id=:id")
    suspend fun updateTotalRooms(id: Int, totalRooms: Int)

    @Query("SELECT * FROM house")
    fun getAllHouse(): Flow<List<HouseEntity>>

    @Query("SELECT * FROM house WHERE id=:id")
    fun getHouse(id: Int): Flow<HouseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(rooms: RoomsEntity)

    @Delete
    suspend fun deleteRoom(room: RoomsEntity)

    @Query("DELETE FROM rooms WHERE parentID=:parentID")
    suspend fun deleteRoomByParentID( parentID: Int )

    @Delete
    suspend fun deleteRooms(rooms:List<RoomsEntity>)

    @Query("SELECT * FROM rooms")
    fun getRooms(): Flow<List<RoomsEntity>>

    @Query("SELECT * FROM rooms WHERE parentID=:parentID")
    fun getRoomsByParentID(parentID:Int):Flow<List<RoomsEntity>>

    @Query("SELECT * FROM rooms WHERE id=:id")
    fun getRoom(id: Int): Flow<RoomsEntity>
}