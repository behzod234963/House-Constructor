package com.mr.anonym.houseconstructor.di

import android.content.Context
import androidx.room.Room
import com.mr.anonym.houseconstructor.data.ConstructorDao
import com.mr.anonym.houseconstructor.data.HouseRepository
import com.mr.anonym.houseconstructor.data.RoomInstance
import com.mr.anonym.houseconstructor.ui.utils.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context):RoomInstance {
        return Room.databaseBuilder(
            context = context,
            RoomInstance::class.java,
            DB_NAME,
        ).build()
    }
    @Provides
    @Singleton
    fun provideDao(db:RoomInstance):ConstructorDao = db.dao
    @Provides
    @Singleton
    fun provideRepository(dao: ConstructorDao):HouseRepository = HouseRepository(dao)
}