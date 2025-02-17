package com.mr.anonym.houseconstructor.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.anonym.houseconstructor.data.HouseRepository
import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.helpers.RoomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repository: HouseRepository
) :ViewModel() {

    private val _houses = mutableStateOf( RoomState().houses)
    val houses:State<List<HouseEntity>> = _houses

    init {
        getAllHome()
    }

    private fun getAllHome() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllHouse().collect {
            _houses.value = it
        }
    }
    fun deleteHome(home:HouseEntity) = viewModelScope.launch (Dispatchers.IO){
        repository.deleteHome(home)
    }
}