package com.example.dragonballwiki

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragonballwiki.core.navigation.Routes
import com.example.dragonballwiki.dragonlist.ui.screen.DragonListScreen
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import com.example.dragonballwiki.ui.theme.DragonBallWikiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val dragonListViewModel: DragonListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragonBallWikiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.DragonList.route
                    ) {
                        composable(route = Routes.DragonList.route) {
                            DragonListScreen(dragonListViewModel = dragonListViewModel,
                                navigationController = navigationController,
                                onClickElement = {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                }, onClickErrorList = {
                                    dragonListViewModel.reloadList()
                                })
                        }
                    }
                }
            }
        }
    }
}