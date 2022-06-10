package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.*



interface ArtistAPI {

    @GET("api/artists/")
    fun GetArtistsList(@Header("Authorization") apiKey:String, ) :
            Call<List<APIArtist>>
}