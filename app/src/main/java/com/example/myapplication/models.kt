package com.example.myapplication

data class AuthToken(
    val username : String,
    val password : String,
    val token : String
)

data class Artist(
    val id: Int,
    val name: String,
    val genre_name: String,
    val album_cover_url : String,
)

data class Genre(
    val id :Int,
    val name : String,
    val created_at: String
)

data class Song(
    val id : Int,
    val name: String,
    val file_url:String,
    val duration:Int,
    val created_at: String,
    val artist: Int
)

