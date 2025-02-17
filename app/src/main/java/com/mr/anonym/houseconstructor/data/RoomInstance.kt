package com.mr.anonym.houseconstructor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.data.model.RoomsEntity

@Database(entities = [HouseEntity::class,RoomsEntity::class], version = 1)
abstract class RoomInstance:RoomDatabase() {
    abstract val dao:ConstructorDao
}