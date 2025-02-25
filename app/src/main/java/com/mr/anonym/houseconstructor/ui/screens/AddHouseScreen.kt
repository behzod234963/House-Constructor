package com.mr.anonym.houseconstructor.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mr.anonym.houseconstructor.R
import com.mr.anonym.houseconstructor.data.model.DataStoreInstance
import com.mr.anonym.houseconstructor.data.model.HouseEntity
import com.mr.anonym.houseconstructor.data.model.RoomsEntity
import com.mr.anonym.houseconstructor.helpers.AddHouseEvent
import com.mr.anonym.houseconstructor.helpers.CalculateCost
import com.mr.anonym.houseconstructor.helpers.CalculateMaterials
import com.mr.anonym.houseconstructor.helpers.roundTo
import com.mr.anonym.houseconstructor.helpers.stringChecker
import com.mr.anonym.houseconstructor.navigation.NavArguments
import com.mr.anonym.houseconstructor.ui.items.RoomItem
import com.mr.anonym.houseconstructor.ui.theme.fontAmidoneGrotesk
import com.mr.anonym.houseconstructor.viewModel.AddHouseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AddHouseScreen(
    navController: NavController,
    arguments: NavArguments,
    viewModel: AddHouseViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val dataStore = DataStoreInstance(context)

    val isError = remember { mutableStateOf(false) }
    val isWidthError = remember { mutableStateOf(false) }
    val isHeightError = remember { mutableStateOf(false) }
    val isLengthError = remember { mutableStateOf(false) }
    val isRoomProcess = remember { mutableStateOf(false) }
    val isConfirmed = remember { mutableStateOf(false) }
    val isCostChange = remember { mutableStateOf(false) }
    val isUpdate = remember { mutableStateOf( false ) }

    val houseName = viewModel.homeName
    val roomName = viewModel.roomName
    val length = viewModel.length
    val width = viewModel.width
    val height = viewModel.height

    val generalCementCost = dataStore.getCementCost().collectAsState(0.0)
    val generalBrickCost = dataStore.getBrickCost().collectAsState(0.0)
    val cementCost = remember { mutableStateOf("") }
    val brickCost = remember { mutableStateOf("") }

    val parentID = viewModel.parentID
    val currentRoomID = remember { mutableIntStateOf(-1) }

    val cement = remember { mutableDoubleStateOf(0.0) }
    val cementWithBag = remember { mutableIntStateOf(0) }
    val sand = remember { mutableDoubleStateOf(0.0) }
    val crushedStone = remember { mutableDoubleStateOf(0.0) }
    val brick = remember { mutableIntStateOf(0) }
    val bricks = remember { mutableIntStateOf(0) }
    val cements = remember { mutableIntStateOf(0) }

    val totalRooms = remember { mutableIntStateOf(0) }
    val totalCement = viewModel.totalCement
    val totalCementWithBag = viewModel.totalCementWithBag
    val totalSand = viewModel.totalSand
    val totalCrushedStone = viewModel.totalCrushedStone
    val totalBrick = viewModel.totalBrick
    val totalCementCost = remember { mutableDoubleStateOf(0.0) }
    val totalBrickCost = remember { mutableDoubleStateOf(0.0) }
    val totalCost = viewModel.totalCost

    val primaryColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    val secondaryColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val tertiaryColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
    val quaternaryColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.White

    val focusManager = LocalFocusManager.current

    val keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next,
    )
    val keyboardActions = KeyboardActions(
        onNext = {
            focusManager.moveFocus(FocusDirection.Next)
        }
    )

    val progressButton = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.anim_progress_button)
    )

    val rooms = viewModel.rooms

    BackHandler {
        if (arguments.id == -1 && rooms.value.isNotEmpty()) {
            viewModel.deleteRooms(rooms.value)
            totalRooms.intValue = 0
            navController.popBackStack()
        } else {
            navController.popBackStack()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = houseName.value,
                    color = secondaryColor,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(fontAmidoneGrotesk)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        if (arguments.id == -1 && rooms.value.isNotEmpty()) {
                            viewModel.deleteRooms(rooms.value)
                            totalRooms.intValue = 0
                            navController.popBackStack()
                        } else {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = secondaryColor,
                        contentDescription = "button back"
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
//            Home name field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                textStyle = TextStyle(
                    color = secondaryColor,
                    fontSize = 18.sp
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddHouseEvent.ChangeHomeName(""))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            tint = secondaryColor,
                            contentDescription = "button clear field"
                        )
                    }
                },
                isError = isError.value,
                singleLine = true,
                supportingText = {
                    if (isError.value) {
                        Text(
                            text = "Пожалуйста введите название"
                        )
                    } else {
                        Text(
                            text = "* обязательное поле"
                        )
                    }
                },
                shape = RoundedCornerShape(7.dp),
                value = houseName.value,
                onValueChange = {
                    viewModel.onEvent(AddHouseEvent.ChangeHomeName(it))
                    isError.value = houseName.value.isEmpty() || houseName.value.isBlank()
                },
                placeholder = {
                    Text(
                        text = "Введите название дома или здания"
                    )
                }
            )
            Spacer(Modifier.height(10.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = tertiaryColor
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clickable { isRoomProcess.value = !isRoomProcess.value },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { isRoomProcess.value = true }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
                            imageVector = Icons.Filled.AddCircle,
                            tint = secondaryColor,
                            contentDescription = "button add room"
                        )
                    }
                    Spacer(Modifier.width(5.dp))
                    Text(
                        modifier = Modifier
                            .clickable { isRoomProcess.value = !isRoomProcess.value },
                        text = "Добавить комнату",
                        color = secondaryColor,
                        fontSize = 16.sp
                    )
                    IconButton(
                        onClick = { isRoomProcess.value = !isRoomProcess.value }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
                            imageVector = if (isRoomProcess.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                            tint = secondaryColor,
                            contentDescription = "button add room"
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            if (isRoomProcess.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
//                    Room name field
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        textStyle = TextStyle(
                            color = secondaryColor,
                            fontSize = 18.sp
                        ),
                        singleLine = true,
                        supportingText = {
                            Text(
                                text = "необязательное поле"
                            )
                        },
                        keyboardActions = keyboardActions,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(AddHouseEvent.ChangeRoomName(""))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    tint = secondaryColor,
                                    contentDescription = "button clear field"
                                )
                            }
                        },
                        shape = RoundedCornerShape(7.dp),
                        value = roomName.value,
                        onValueChange = {
                            viewModel.onEvent(AddHouseEvent.ChangeRoomName(it))
                        },
                        placeholder = {
                            Text(
                                text = "Введите название комнаты"
                            )
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                    Width
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(if (!isWidthError.value) 75.dp else 90.dp)
                                .padding(end = 5.dp),
                            textStyle = TextStyle(
                                color = secondaryColor,
                                fontSize = 18.sp
                            ),
                            keyboardActions = keyboardActions,
                            keyboardOptions = keyboardOptions,
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.onEvent(AddHouseEvent.ChangeWidth("")) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        tint = secondaryColor,
                                        contentDescription = "button clear field"
                                    )
                                }
                            },
                            isError = isWidthError.value,
                            singleLine = true,
                            supportingText = {
                                if (isWidthError.value) {
                                    Text(
                                        text = "Пожалуйста введите ширину"
                                    )
                                } else {
                                    Text(
                                        text = "* обязательное поле"
                                    )
                                    isWidthError.value = false
                                }
                            },
                            shape = RoundedCornerShape(7.dp),
                            value = width.value,
                            onValueChange = {
                                viewModel.onEvent(AddHouseEvent.ChangeWidth(it))
                                isWidthError.value = !it.stringChecker()
                            },
                            placeholder = {
                                Text(
                                    text = "Ширина"
                                )
                            }
                        )
//                        Length
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (!isLengthError.value) 75.dp else 90.dp)
                                .padding(start = 5.dp),
                            textStyle = TextStyle(
                                color = secondaryColor,
                                fontSize = 18.sp
                            ),
                            keyboardActions = keyboardActions,
                            keyboardOptions = keyboardOptions,
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.onEvent(AddHouseEvent.ChangeLength("")) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        tint = secondaryColor,
                                        contentDescription = "button clear field"
                                    )
                                }
                            },
                            isError = isLengthError.value,
                            singleLine = true,
                            supportingText = {
                                if (isLengthError.value) {
                                    Text(
                                        text = "Пожалуйста введите длину"
                                    )
                                } else {
                                    Text(
                                        text = "* обязательное поле"
                                    )
                                    isLengthError.value = false
                                }
                            },
                            shape = RoundedCornerShape(7.dp),
                            value = length.value,
                            onValueChange = {
                                viewModel.onEvent(AddHouseEvent.ChangeLength(it))
                                isLengthError.value = !it.stringChecker()
                            },
                            placeholder = {
                                Text(
                                    text = "Длина"
                                )
                            }
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                    Height
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(if (!isHeightError.value) 75.dp else 90.dp)
                                .padding(end = 5.dp),
                            textStyle = TextStyle(
                                color = secondaryColor,
                                fontSize = 18.sp
                            ),
                            keyboardActions = keyboardActions,
                            keyboardOptions = keyboardOptions,
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.onEvent(AddHouseEvent.ChangeHeight("")) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        tint = secondaryColor,
                                        contentDescription = "button clear field"
                                    )
                                }
                            },
                            isError = isHeightError.value,
                            singleLine = true,
                            supportingText = {
                                if (isHeightError.value) {
                                    Text(
                                        text = "Пожалуйста введите высоту"
                                    )
                                } else {
                                    Text(
                                        text = "* обязательное поле"
                                    )
                                    isHeightError.value = false
                                }
                            },
                            shape = RoundedCornerShape(7.dp),
                            value = height.value,
                            onValueChange = {
                                viewModel.onEvent(AddHouseEvent.ChangeHeight(it))
                                isHeightError.value = !it.stringChecker()
                            },
                            placeholder = {
                                Text(
                                    text = "Высота"
                                )
                            }
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    if (
                                        !isError.value
                                        && roomName.value.isNotEmpty()
                                        && !isHeightError.value
                                        && !isLengthError.value
                                        && !isWidthError.value
                                    ) {
                                        isConfirmed.value = true
                                        val calculateMaterials = CalculateMaterials(
                                            width = width.value.toDouble(),
                                            height = height.value.toDouble(),
                                            length = length.value.toDouble(),
                                        )
                                        cement.doubleValue = calculateMaterials.cement().roundTo(2)
                                        sand.doubleValue = calculateMaterials.sand().roundTo(2)
                                        crushedStone.doubleValue = calculateMaterials.crushedStone().roundTo(2)
                                        cementWithBag.intValue = calculateMaterials.cementWithBag()
                                        brick.intValue = calculateMaterials.brick()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Проверьте правильность запольнения полей",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    imageVector = Icons.Filled.CheckCircle,
                                    tint = secondaryColor,
                                    contentDescription = "button confirm"
                                )
                            }
                        }
                    }
                }
            }
            if (isConfirmed.value && roomName.value.isNotEmpty() && !isError.value) {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(3000)
                    isRoomProcess.value = false
                    isConfirmed.value = false
                    val calculateCost = CalculateCost(
                        cementWithBag = cementWithBag.intValue,
                        brick = brick.intValue
                    )
                    totalCementCost.doubleValue = calculateCost.cementCost(generalCementCost.value)
                    totalBrickCost.doubleValue = calculateCost.brickCost(generalBrickCost.value)
                    if (!isUpdate.value){
                        if (arguments.id != -1) {
                            viewModel.insertRoom(
                                RoomsEntity(
                                    parentID = arguments.parentID,
                                    roomName = roomName.value,
                                    length = length.value.toDouble(),
                                    width = width.value.toDouble(),
                                    height = height.value.toDouble(),
                                    cement = cement.doubleValue,
                                    cementWithBag = cementWithBag.intValue,
                                    sand = sand.doubleValue,
                                    crushedStone = crushedStone.doubleValue,
                                    brick = brick.intValue,
                                    cementCost = totalCementCost.doubleValue,
                                    brickCost = totalBrickCost.doubleValue
                                )
                            )
                            viewModel.onEvent(AddHouseEvent.ChangeWidth(""))
                            viewModel.onEvent(AddHouseEvent.ChangeHeight(""))
                            viewModel.onEvent(AddHouseEvent.ChangeLength(""))
                            viewModel.onEvent(AddHouseEvent.ChangeRoomName(""))
                            viewModel.getRoomsByParentID(arguments.parentID)
                            totalRooms.intValue++
                        } else {
                            viewModel.insertRoom(
                                RoomsEntity(
                                    parentID = parentID,
                                    roomName = roomName.value,
                                    length = length.value.toDouble(),
                                    width = width.value.toDouble(),
                                    height = height.value.toDouble(),
                                    cement = cement.doubleValue,
                                    cementWithBag = cementWithBag.intValue,
                                    sand = sand.doubleValue,
                                    crushedStone = crushedStone.doubleValue,
                                    brick = brick.intValue,
                                    cementCost = totalCementCost.doubleValue,
                                    brickCost = totalBrickCost.doubleValue
                                )
                            )
                            viewModel.onEvent(AddHouseEvent.ChangeWidth(""))
                            viewModel.onEvent(AddHouseEvent.ChangeHeight(""))
                            viewModel.onEvent(AddHouseEvent.ChangeLength(""))
                            viewModel.onEvent(AddHouseEvent.ChangeRoomName(""))
                            viewModel.getRoomsByParentID(parentID)
                            totalRooms.intValue++
                        }
                    }else{
                        viewModel.insertRoom(
                            RoomsEntity(
                                id = currentRoomID.intValue,
                                parentID = arguments.parentID,
                                roomName = roomName.value,
                                length = length.value.toDouble(),
                                width = width.value.toDouble(),
                                height = height.value.toDouble(),
                                cement = cement.doubleValue,
                                cementWithBag = cementWithBag.intValue,
                                sand = sand.doubleValue,
                                crushedStone = crushedStone.doubleValue,
                                brick = brick.intValue,
                                cementCost = totalCementCost.doubleValue,
                                brickCost = totalBrickCost.doubleValue
                            )
                        )
                        viewModel.onEvent(AddHouseEvent.ChangeWidth(""))
                        viewModel.onEvent(AddHouseEvent.ChangeHeight(""))
                        viewModel.onEvent(AddHouseEvent.ChangeLength(""))
                        viewModel.onEvent(AddHouseEvent.ChangeRoomName(""))
                        viewModel.getRoomsByParentID(parentID)
                        isUpdate.value = false
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            LazyColumn {
                items(rooms.value) { room ->
                    if (!isCostChange.value){
                        currentRoomID.intValue = room.id ?: -1
                    }
                    bricks.intValue = room.brick
                    cements.intValue = room.cementWithBag
                    RoomItem(
                        primaryColor = quaternaryColor,
                        secondaryColor = secondaryColor,
                        roomName = room.roomName,
                        cement = room.cement,
                        cementWithBag = room.cementWithBag,
                        sand = room.sand,
                        crushedStone = room.crushedStone,
                        brick = room.brick,
                        length = room.length,
                        width = room.width,
                        height = room.height,
                        cementCost = room.cementCost,
                        brickCost = room.brickCost,
                        onCostClick = {
                            currentRoomID.intValue = room.id ?: -1
                            isCostChange.value = true
                        },
                        onEditClick = {
                            viewModel.onEvent(AddHouseEvent.ChangeRoomName(room.roomName))
                            viewModel.onEvent(AddHouseEvent.ChangeWidth(room.width.toString()))
                            viewModel.onEvent(AddHouseEvent.ChangeHeight(room.height.toString()))
                            viewModel.onEvent(AddHouseEvent.ChangeLength(room.length.toString()))
                            isUpdate.value = true
                            currentRoomID.intValue = room.id ?: -1
                            isRoomProcess.value = true
                        },
                        onDeleteClick = {
                            viewModel.deleteRoom(room)
                            totalRooms.intValue--
                        }
                    )
                }
            }
        }
    }
    if (isConfirmed.value) {
        Box {
            LottieAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                composition = progressButton.value
            )
        }
    }
    if (isCostChange.value) {
        Box(Modifier.fillMaxSize()) {
            OnCostContent(
                secondaryColor = secondaryColor,
                quaternaryColor = quaternaryColor,
                cementCost = cementCost.value,
                brickCost = brickCost.value,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                confirmButton = {
                    if (
                        cementCost.value.isNotEmpty()
                        && brickCost.value.isNotEmpty()
                    ) {
                        if (arguments.id == -1) {
                            val calculateCost = CalculateCost(
                                brick = brick.intValue,
                                cementWithBag = cementWithBag.intValue
                            )
                            totalCementCost.doubleValue =
                                calculateCost.cementCost(cementCost.value.toDouble())
                            totalBrickCost.doubleValue =
                                calculateCost.brickCost(brickCost.value.toDouble())
                            viewModel.updateBrickCost(
                                currentRoomID.intValue,
                                totalBrickCost.doubleValue
                            )
                            viewModel.updateCementCost(
                                currentRoomID.intValue,
                                totalCementCost.doubleValue
                            )
                            viewModel.getRoomsByParentID(parentID)
                            brickCost.value = ""
                            cementCost.value = ""
                            totalCementCost.doubleValue = 0.0
                            totalBrickCost.doubleValue = 0.0
                            isCostChange.value = false
                        } else {
                            val calculateCost = CalculateCost(
                                brick = bricks.intValue,
                                cementWithBag = cements.intValue
                            )
                            totalCementCost.doubleValue =
                                calculateCost.cementCost(cementCost.value.toDouble())
                            totalBrickCost.doubleValue =
                                calculateCost.brickCost(brickCost.value.toDouble())
                            viewModel.updateBrickCost(
                                currentRoomID.intValue,
                                totalBrickCost.doubleValue
                            )
                            viewModel.updateCementCost(
                                currentRoomID.intValue,
                                totalCementCost.doubleValue
                            )
                            viewModel.getRoomsByParentID(arguments.parentID)
                            totalCementCost.doubleValue = 0.0
                            totalBrickCost.doubleValue = 0.0
                            isCostChange.value = false
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Проверьте правильность запольнения полей",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                dismissButton = {
                    cementCost.value = ""
                    brickCost.value = ""
                    totalCementCost.doubleValue = 0.0
                    totalBrickCost.doubleValue = 0.0
                    isCostChange.value = false
                },
                onDismissRequest = {
                    cementCost.value = ""
                    brickCost.value = ""
                    totalCementCost.doubleValue = 0.0
                    totalBrickCost.doubleValue = 0.0
                    isCostChange.value = false
                },
                onCementCostChange = {
                    cementCost.value = it
                },
                onBrickCostChange = {
                    brickCost.value = it
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 10.dp, bottom = 20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                containerColor = secondaryColor,
                onClick = {
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    val currentDate = dateFormat.format(Calendar.getInstance().time)
                    viewModel.calculateMaterials()
                    viewModel.calculateCost()
                    if (arguments.id != -1) {
                        viewModel.insertHome(
                            HouseEntity(
                                id = arguments.id,
                                parentID = arguments.parentID,
                                houseName = houseName.value,
                                date = currentDate,
                                totalRooms = rooms.value.size,
                                totalCement = totalCement.value,
                                totalCementWithBag = totalCementWithBag.value,
                                totalSand = totalSand.value,
                                totalCrushedStone = totalCrushedStone.value,
                                totalBrick = totalBrick.value,
                                totalCost = totalCost.value
                            )
                        )
                    } else {
                        viewModel.insertHome(
                            HouseEntity(
                                parentID = parentID,
                                houseName = houseName.value,
                                date = currentDate,
                                totalRooms = rooms.value.size,
                                totalCement = totalCement.value,
                                totalCementWithBag = totalCementWithBag.value,
                                totalSand = totalSand.value,
                                totalCrushedStone = totalCrushedStone.value,
                                totalBrick = totalBrick.value,
                                totalCost = totalCost.value
                            )
                        )
                    }
                    navController.popBackStack()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_save),
                    tint = primaryColor,
                    contentDescription = "button save"
                )
            }
        }
    }
}

@Composable
fun OnCostContent(
    secondaryColor: Color,
    quaternaryColor: Color,
    cementCost: String,
    brickCost: String,
    keyboardActions:KeyboardActions,
    keyboardOptions: KeyboardOptions,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    onDismissRequest: () -> Unit,
    onCementCostChange: (String) -> Unit,
    onBrickCostChange: (String) -> Unit,
) {

    val isCementCostError = remember { mutableStateOf(false) }
    val isBrickCostError = remember { mutableStateOf(false) }

    AlertDialog(
        containerColor = quaternaryColor,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
//                    Cement cost field
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (!isCementCostError.value) 75.dp else 90.dp)
                        .padding(start = 5.dp),
                    textStyle = TextStyle(
                        color = secondaryColor,
                        fontSize = 18.sp
                    ),
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    isError = isCementCostError.value,
                    singleLine = true,
                    supportingText = {
                        if (isCementCostError.value) {
                            Text(
                                text = "Пожалуйста введите цену"
                            )
                        } else {
                            Text(
                                text = "Цена за 1 мешок цемент"
                            )
                            isCementCostError.value = false
                        }
                    },
                    shape = RoundedCornerShape(7.dp),
                    value = cementCost,
                    onValueChange = {
                        onCementCostChange(it)
                        isCementCostError.value = !it.stringChecker()
                    },
                    placeholder = {
                        Text(
                            text = "Цена за 1 мешок цемент"
                        )
                    }
                )
//                Brick cost field
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (!isBrickCostError.value) 75.dp else 90.dp)
                        .padding(start = 5.dp),
                    textStyle = TextStyle(
                        color = secondaryColor,
                        fontSize = 18.sp
                    ),
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    isError = isBrickCostError.value,
                    singleLine = true,
                    supportingText = {
                        if (isBrickCostError.value) {
                            Text(
                                text = "Пожалуйста введите цену"
                            )
                        } else {
                            Text(
                                text = "Цена за 1 шт кирпич"
                            )
                            isBrickCostError.value = false
                        }
                    },
                    shape = RoundedCornerShape(7.dp),
                    value = brickCost,
                    onValueChange = {
                        onBrickCostChange(it)
                        isBrickCostError.value = !it.stringChecker()
                    },
                    placeholder = {
                        Text(
                            text = "Цена за 1 шт кирпич"
                        )
                    }
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { confirmButton() }
            ) {
                Text(
                    text = "Сохранить",
                    color = Color.Green,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { dismissButton() }
            ) {
                Text(
                    text = "Выйти",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
    )
}