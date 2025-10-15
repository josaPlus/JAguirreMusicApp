package com.example.jaguirremusicapp.screens

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute


@Serializable
data class AlbumDetailScreenRoute(val id: String)