package com.example.dragonballwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dragonballwiki.core.navigation.NavigationHost
import com.example.dragonballwiki.ui.theme.DragonBallWikiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragonBallWikiTheme {
                Surface(
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    NavigationHost()
                }
            }
        }
    }
}