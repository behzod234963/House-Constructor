package com.mr.anonym.houseconstructor.data.model

import android.content.Context
import android.preference.PreferenceDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore:DataStore<Preferences> by preferencesDataStore("constructor")
class DataStoreInstance (private val context: Context){

    suspend fun saveCementCost(cost:Double){
        val key = doublePreferencesKey("cementCost")
        context.dataStore.edit {
            it[key] = cost
        }
    }
    fun getCementCost():Flow<Double>{
        val key = doublePreferencesKey("cementCost")
        return context.dataStore.data.map {
            it[key]?:45000.0
        }
    }
    suspend fun saveBrickCost(cost:Double){
        val key = doublePreferencesKey("brickCost")
        context.dataStore.edit {
            it[key] = cost
        }
    }
    fun getBrickCost():Flow<Double>{
        val key = doublePreferencesKey("brickCost")
        return context.dataStore.data.map {
            it[key]?:500.0
        }
    }
}