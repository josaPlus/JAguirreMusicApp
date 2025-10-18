package com.example.jaguirremusicapp.screens

import android.util.Log
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jaguirremusicapp.components.AlbumCard
import com.example.jaguirremusicapp.components.CarouselAlbum
import com.example.jaguirremusicapp.components.Header
import com.example.jaguirremusicapp.components.ListaAlbums
import com.example.jaguirremusicapp.models.Album
import com.example.jaguirremusicapp.services.AlbumService
import com.example.jaguirremusicapp.ui.theme.JAguirreMusicAppTheme
import com.example.jaguirremusicapp.ui.theme.gradient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(
    navController: NavController,
    onSongClick: (Album) -> Unit = {}
){

    val BASE_URL = "https://music.juanfrausto.com/"
    var albums by remember { mutableStateOf(listOf<Album>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllAlbums() }
            albums = result
        } catch (e: Exception) {
            Log.e("HomeScreen", "Algo falló: ${e}")
        } finally {
            loading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //HEADER
        Header()

        if (loading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF4C06A6))
            }
        }else{

            // CAROUSEL DE ALBUMS
            CarouselAlbum(navController, onSongClick)

            // ALBUMS EN LISTA DESPLAZABLE
            LazyColumn(
                modifier = Modifier.padding(10.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Recently Played",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "See more",
                            color = Color(0xFF673AB7),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                items(albums){ album ->
                    ListaAlbums(
                        album, {
                            onSongClick(album)
                            navController.navigate(AlbumDetailScreenRoute(album.id))
                        }
                    )
                }
            }

        }
        
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PHONE
)
@Composable
fun HomeScreenPreview(){
    JAguirreMusicAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}