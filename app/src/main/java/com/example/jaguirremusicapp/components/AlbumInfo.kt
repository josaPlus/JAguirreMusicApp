package com.example.jaguirremusicapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jaguirremusicapp.models.Album
import com.example.jaguirremusicapp.ui.theme.JAguirreMusicAppTheme

@Composable
fun AlbumInfo(album: Album) {
    Column (
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // This adds space between them
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp), // Padding is now inside the items
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "About this album",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = album.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp), // Padding is now inside the items
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEADDFF))
        ) {
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text("Artist: ", fontWeight = FontWeight.Bold)
                Text(album.artist)
            }
        }
    }
}

@Preview
@Composable
fun si(){
    JAguirreMusicAppTheme {
        val album = Album("1", "sds", "aas", "sdsds", "dhdh")
        AlbumInfo(album)
    }
}