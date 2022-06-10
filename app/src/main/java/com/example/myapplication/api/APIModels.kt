package com.example.myapplication.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class APIUserInfo(val username : String, val password : String)

@JsonClass(generateAdapter = true)
data class APIToken (val token : String)

@JsonClass(generateAdapter = true)
data class APIArtistList(
    val id : Int,
    val name : String,
    val genre_name : String,
    val album_cover_url : String,
)