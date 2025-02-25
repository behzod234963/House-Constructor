package com.mr.anonym.houseconstructor.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mr.anonym.houseconstructor.R
import com.mr.anonym.houseconstructor.data.model.DataStoreInstance
import com.mr.anonym.houseconstructor.navigation.Screens
import com.mr.anonym.houseconstructor.ui.items.HomeItem
import com.mr.anonym.houseconstructor.ui.theme.fontAmidoneGrotesk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val dataStore = DataStoreInstance(context)

    val isCostChange = remember { mutableStateOf( false ) }

    val cementCost = remember { mutableStateOf( "" ) }
    val brickCost = remember { mutableStateOf( "" ) }

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


    val houses = viewModel.houses

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "House constructor",
                        color = secondaryColor,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(fontAmidoneGrotesk)
                    )
                },
                actions = {
                    IconButton(
                    onClick = { isCostChange.value = true }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cost),
                        tint = secondaryColor,
                        contentDescription = "button edit room"
                    )
                } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = secondaryColor,
                onClick = {
                    navController.navigate(Screens.AddHouseScreen.route + "/${-1}/${-1}")
                },
                elevation = FloatingActionButtonDefaults.elevation(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = primaryColor,
                    contentDescription = "button add"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(houses.value) { entity ->
                HomeItem(
                    primaryColor = quaternaryColor,
                    secondaryColor = secondaryColor,
                    tertiaryColor = tertiaryColor,
                    onClick = { navController.navigate(Screens.AddHouseScreen.route + "/${entity.id}/${entity.parentID}") },
                    onDeleteClick = {
                        viewModel.deleteHome(entity)
                        viewModel.deleteRoomByParentID(entity.parentID)
                    },
                    entity = entity
                )
            }
        }
    }
    if (isCostChange.value){
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
                        CoroutineScope(Dispatchers.Default).launch {
                            dataStore.saveBrickCost(brickCost.value.toDouble())
                            dataStore.saveCementCost(cementCost.value.toDouble())
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
                    isCostChange.value = false
                },
                onDismissRequest = {
                    cementCost.value = ""
                    brickCost.value = ""
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
}