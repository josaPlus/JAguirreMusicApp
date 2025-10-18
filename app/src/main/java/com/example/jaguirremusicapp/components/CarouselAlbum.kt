package com.example.jaguirremusicapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jaguirremusicapp.models.Album
import com.example.jaguirremusicapp.screens.AlbumDetailScreenRoute
import com.example.jaguirremusicapp.services.AlbumService
import com.example.jaguirremusicapp.ui.theme.JAguirreMusicAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun CarouselAlbum(
    navController: NavController,
    onSongClick: (Album) -> Unit
){

    val BASE_URL = "https://music.juanfrausto.com/"
    var albums by remember { mutableStateOf(listOf<Album>()) }

    LaunchedEffect(key1 = true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllAlbums() }
            albums = result
        } catch (e: Exception) {
            Log.e("CarouselAlbum", "Error al cargar álbumes: ${e.message}")
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Albums",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See more",
                color = Color(0xFF673AB7),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {  }
            )
        }

        //CAROUSEL
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(albums){ album ->
                AlbumCard(album, {
                    onSongClick(album)
                    navController.navigate(AlbumDetailScreenRoute(album.id))
                })
            }
        }
    }
}

@Preview
@Composable
fun carousel(){
    JAguirreMusicAppTheme {
        val navController = rememberNavController()
        CarouselAlbum(navController, {})
    }
}