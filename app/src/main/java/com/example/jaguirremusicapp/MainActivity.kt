package com.example.jaguirremusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
            JAguirreMusicAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = HomeScreenRoute){
                        composable<HomeScreenRoute> {
                            HomeScreen(navController)
                        }
                        composable<AlbumDetailScreenRoute> { backEntry ->
                            val args = backEntry.toRoute<AlbumDetailScreenRoute>()
                            AlbumDetailScreen(args.id)
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