package com.mr.anonym.houseconstructor.navigation

sealed class Screens(val route:String) {

    data object HomeScreen:Screens(route = "HomeScreen")
    data object AddHouseScreen:Screens(route = "NewHouseScreen")
}