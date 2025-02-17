package com.mr.anonym.houseconstructor.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mr.anonym.houseconstructor.navigation.Screens
import com.mr.anonym.houseconstructor.ui.items.HomeItem
import com.mr.anonym.houseconstructor.ui.theme.fontAmidoneGrotesk

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val primaryColor = if(isSystemInDarkTheme()) Color.Black else Color.White
    val secondaryColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val tertiaryColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

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
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = secondaryColor,
                onClick = {
                    navController.navigate(Screens.AddHouseScreen.route + "/${-1}/${-1}")
                },
                elevation = FloatingActionButtonDefaults.elevation( 5.dp )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = primaryColor,
                    contentDescription = "button add"
                )
            }
        }
    ) {paddingValues->
        LazyColumn (
            modifier = Modifier
                .padding(paddingValues)
        ){
            items(houses.value){entity->
                HomeItem(
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    tertiaryColor = tertiaryColor,
                    onClick = { navController.navigate(Screens.AddHouseScreen.route + "/${entity.id}/${entity.parentID}") },
                    onDeleteClick = { viewModel.deleteHome(entity) },
                    entity = entity
                )
            }
        }
    }
}