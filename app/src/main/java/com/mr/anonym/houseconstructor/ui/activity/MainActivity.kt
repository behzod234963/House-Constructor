package com.mr.anonym.houseconstructor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mr.anonym.houseconstructor.navigation.NavGraph
import com.mr.anonym.houseconstructor.ui.theme.HouseConstructorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HouseConstructorTheme {
                NavGraph()
            }
        }
    }
}