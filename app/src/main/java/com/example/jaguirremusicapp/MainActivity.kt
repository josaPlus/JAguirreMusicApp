package com.example.jaguirremusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jaguirremusicapp.components.BarMusic
import com.example.jaguirremusicapp.models.Album
import com.example.jaguirremusicapp.screens.AlbumDetailScreen
import com.example.jaguirremusicapp.screens.AlbumDetailScreenRoute
import com.example.jaguirremusicapp.screens.HomeScreen
import com.example.jaguirremusicapp.screens.HomeScreenRoute
import com.example.jaguirremusicapp.ui.theme.JAguirreMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var currentlyPlayingAlbum by remember { mutableStateOf<Album?>(null) }
            JAguirreMusicAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentlyPlayingAlbum != null) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .padding(bottom = 12.dp)
                            ) {
                                BarMusic(currentlyPlayingAlbum!!)
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = HomeScreenRoute){
                        composable<HomeScreenRoute> {
                            HomeScreen(navController, onSongClick = { album ->
                                currentlyPlayingAlbum = album
                            })
                        }
                        composable<AlbumDetailScreenRoute> { backEntry ->
                            val args = backEntry.toRoute<AlbumDetailScreenRoute>()
                            AlbumDetailScreen(args.id, navController, onSongClick = { album ->
                                currentlyPlayingAlbum = album
                            })
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JAguirreMusicAppTheme {

    }
}