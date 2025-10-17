package com.example.jaguirremusicapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.jaguirremusicapp.models.Album
import com.example.jaguirremusicapp.ui.theme.JAguirreMusicAppTheme
import android.util.Log
import androidx.compose.foundation.clickable

@Composable
fun ListaAlbums(album: Album, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable{onClick()},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),

    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            // Imagen del álbum
            AsyncImage(
                model = album.image,
                contentDescription = album.title,
                contentScale = ContentScale.Crop,
                onError = { error ->
                    Log.e("CoilError", "Error al cargar imagen: ${album.image}, Razón: ${error.result.throwable}")
                },
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // Columna con título y artista
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = album.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    // Añadimos el texto "Popular Song" como en el diseño
                    text = "${album.artist} • Popular Song",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Icono de tres puntos
            IconButton(onClick = {  }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun ListaAlbumPreview(){
    JAguirreMusicAppTheme {
        val sampleAlbum = Album(
            id = "1",
            title = "Título de Muestra",
            artist = "Artista de Prueba",
            description = "Descripción para la vista previa.",
            image = "" // URL vacía para que AsyncImage no cargue nada
        )
        ListaAlbums(sampleAlbum, {})
    }
}