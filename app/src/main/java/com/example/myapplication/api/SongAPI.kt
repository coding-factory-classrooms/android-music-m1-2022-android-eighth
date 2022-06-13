package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SongAPI {
    @GET("api/songs/")
    fun GetSongsList(@Header("Authorization") apiKey:String) :
            Call<List<APISong>>

    @GET("api/songs/{id}/")
    fun GetSongById(@Header("Authorization") apiKey:String,@Path("id") SongId:Int ) :
            Call<List<APISong>>
}