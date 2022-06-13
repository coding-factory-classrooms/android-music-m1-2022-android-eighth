package com.example.myapplication.api

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class APIUserInfo(val username : String, val password : String)

@JsonClass(generateAdapter = true)
data class APIToken (val token : String)

@JsonClass(generateAdapter = true)
@Parcelize
data class APIArtist(
    val id : Int,
    val name : String,
    val genre_name : String,
    val album_cover_url : String,
):Parcelable

@JsonClass(generateAdapter = true)
data class APISong(
    val id : Int,
    val name : String,
    val file : String,
    val duration : Int,
    val created_at : String,
    val artist : Int,
)