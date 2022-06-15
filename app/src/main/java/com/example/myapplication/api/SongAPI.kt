package com.example.myapplication.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SongAPI {
    @GET("api/songs/")
    fun GetSongsList(@Header("Authorization") apiKey:String) :
            Call<List<APISong>>

    @GET("api/songs/{id}/")
    fun GetSongById(@Header("Authorization") apiKey:String,@Path("id") SongId:Int ) :
            Call<List<APISong>>

    @GET
    suspend fun fileDownload(@Url fileUrl:String): Response<ResponseBody>

}