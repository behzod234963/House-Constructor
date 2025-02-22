package com.mr.anonym.houseconstructor.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.anonym.houseconstructor.data.HouseRepository
import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.data.model.RoomsEntity
import com.mr.anonym.houseconstructor.helpers.AddHouseEvent
import com.mr.anonym.houseconstructor.helpers.RoomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHouseViewModel @Inject constructor(
    savedState:SavedStateHandle,
    private val repository: HouseRepository
) : ViewModel() {

    private val range = 0..999999
    val parentID = range.random()

    private val _homeName = mutableStateOf("")
    val homeName: State<String> = _homeName

    private val _roomName = mutableStateOf("")
    val roomName: State<String> = _roomName

    private val _length = mutableStateOf("")
    val length: State<String> = _length

    private val _width = mutableStateOf("")
    val width: State<String> = _width

    private val _height = mutableStateOf("")
    val height: State<String> = _height

    private val _totalCement = mutableDoubleStateOf(0.0)
    val totalCement: State<Double> = _totalCement

    private val _totalCementWithBag = mutableIntStateOf(0)
    val totalCementWithBag: State<Int> = _totalCementWithBag

    private val _totalSand = mutableDoubleStateOf(0.0)
    val totalSand: State<Double> = _totalSand

    private val _totalCrushedStone = mutableDoubleStateOf(0.0)
    val totalCrushedStone: State<Double> = _totalCrushedStone

    private val _totalBrick = mutableIntStateOf(0)
    val totalBrick: State<Int> = _totalBrick

    private val _totalCost = mutableDoubleStateOf(0.0)
    val totalCost: State<Double> = _totalCost

    private val _homeModel = mutableStateOf(RoomState().homeModel)
    val homeModel: State<HouseEntity?> = _homeModel

    private val _rooms = mutableStateOf(RoomState().rooms)
    val rooms: State<List<RoomsEntity>> = _rooms

    init {
        viewModelScope.launch {
            savedState.get<Int>("id")?.let {id->
                if (id != -1){
                    getHome(id)
                }
            }
            savedState.get<Int>("parentID")?.let {pID->
                if (pID != -1){
                    getRoomsByParentID(pID)
                }
            }
        }
    }

    fun onEvent(event: AddHouseEvent) {
        when (event) {
            is AddHouseEvent.ChangeHeight -> {
                _height.value = event.height
            }

            is AddHouseEvent.ChangeHomeName -> {
                _homeName.value = event.homeName
            }

            is AddHouseEvent.ChangeLength -> {
                _length.value = event.length
            }

            is AddHouseEvent.ChangeRoomName -> {
                _roomName.value = event.roomName
            }

            is AddHouseEvent.ChangeWidth -> {
                _width.value = event.width
            }
        }
    }

    private fun getHome(id:Int){
        viewModelScope.launch (Dispatchers.IO){
            repository.getHouse(id).collect{
                _homeModel.value = it
                _homeName.value = it.houseName
            }
        }
    }

    fun insertHome(home: HouseEntity) = viewModelScope.launch {
        repository.insertHome(house = home)
    }

    fun insertRoom(room: RoomsEntity) = viewModelScope.launch {
        repository.insertRoom(room)
    }
    fun updateCementCost(id:Int,cost:Double) = viewModelScope.launch (Dispatchers.IO){
        repository.updateCementCost(id, cost)
    }
    fun updateBrickCost(id:Int,cost:Double) = viewModelScope.launch (Dispatchers.IO){
        repository.updateBrickCost(id, cost)
    }

    fun deleteRoom(room: RoomsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRoom(room)
    }

    fun deleteRooms(rooms:List<RoomsEntity>) = viewModelScope.launch {
        repository.deleteRooms(rooms)
    }

    fun getRoomsByParentID(parentID: Int) = viewModelScope.launch (Dispatchers.IO){
        repository.getRoomsByParentID(parentID).collect {
            _rooms.value = it
        }
    }

    fun calculateMaterials() = viewModelScope.launch {
        _rooms.value.forEach { room ->
            if (room.cement <= 0.0) _totalCement.doubleValue =
                room.cement else _totalCement.doubleValue += room.cement
            if (room.brick <= 0) _totalBrick.intValue =
                room.brick else _totalBrick.intValue += room.brick
            if (room.cementWithBag <= 0) _totalCementWithBag.intValue =
                room.cementWithBag else _totalCementWithBag.intValue += room.cementWithBag
            if (room.sand <= 0.0) _totalSand.doubleValue =
                room.sand else _totalSand.doubleValue += room.sand
            if (room.crushedStone <= 0.0) _totalCrushedStone.doubleValue =
                room.crushedStone else _totalCrushedStone.doubleValue += room.crushedStone
        }
    }
    fun calculateCost() = viewModelScope.launch {
        _rooms.value.forEach { room ->
            _totalCost.doubleValue += room.cementCost + room.brickCost
        }
    }
}