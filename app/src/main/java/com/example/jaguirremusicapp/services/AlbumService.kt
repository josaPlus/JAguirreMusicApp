package com.example.jaguirremusicapp.services

import com.example.jaguirremusicapp.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    // OBTENER TODOS LOS ALBUMS
    @GET("api/albums")
    suspend fun getAllAlbums(): List<Album>

    // OBTENER ALBUM POR ID
    @GET("api/albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Album
}