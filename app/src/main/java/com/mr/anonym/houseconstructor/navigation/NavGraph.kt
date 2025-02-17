package com.mr.anonym.houseconstructor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mr.anonym.houseconstructor.ui.screens.AddHouseScreen
import com.mr.anonym.houseconstructor.ui.screens.HomeScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ){
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screens.AddHouseScreen.route + "/{id}/{parentID}",
            arguments = listOf(
                navArgument(name = "id"){
                type = NavType.IntType
                defaultValue = -1
            },
                navArgument( name = "parentID" ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {entry->
            val id = entry.arguments?.getInt("id")?:-1
            val parentID = entry.arguments?.getInt("parentID")?:-1
            AddHouseScreen(
                navController = navController,
                arguments = NavArguments(id = id,parentID)
            )
        }
    }
}