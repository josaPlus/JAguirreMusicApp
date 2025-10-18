package com.example.jaguirremusicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jaguirremusicapp.components.AlbumArt
import com.example.jaguirremusicapp.components.AlbumInfo
import com.example.jaguirremusicapp.components.ListaAlbums
import com.example.jaguirremusicapp.models.Album
import com.example.jaguirremusicapp.services.AlbumService
import com.example.jaguirremusicapp.ui.theme.JAguirreMusicAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AlbumDetailScreen(
    id: String,
    navController: NavController,
    onSongClick: (Album) -> Unit = {}
) {
    var album by remember { mutableStateOf<Album?>(null) }
    var loading by remember { mutableStateOf(true) }

    var allAlbums by remember { mutableStateOf(listOf<Album>()) }

    LaunchedEffect(key1 = id) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://music.juanfrausto.com/") // La URL base correcta
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(AlbumService::class.java)

            val resultAlbum = withContext(Dispatchers.IO) { service.getAlbumById(id) }
            val resultAllAlbums = withContext(Dispatchers.IO) { service.getAllAlbums() } // <-- 3. LLAMADA PARA TODOS LOS ÁLBUMES

            album = resultAlbum
            allAlbums = resultAllAlbums
        } catch (e: Exception) {
            Log.e("AlbumDetailScreen", "Error fetching details: ${e.message}")
        } finally {
            loading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F7))
    ) {
        if (loading) {
        } else if (album != null) {
            item {
                AlbumArt(album = album!!)
            }
            item {
                AlbumInfo(album = album!!)
            }

            item {
                Text(
                    text = "More Albums",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                )
            }

            items(allAlbums) { albumFromList ->
                ListaAlbums(
                    album = albumFromList,
                    onClick = {
                        onSongClick(albumFromList)
                        navController.navigate(AlbumDetailScreenRoute(id = albumFromList.id))
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PHONE
)
@Composable
fun AlbumDetailScreenPreview(){
    JAguirreMusicAppTheme {
        val navController = rememberNavController()
        AlbumDetailScreen("prueba", navController)
    }
}