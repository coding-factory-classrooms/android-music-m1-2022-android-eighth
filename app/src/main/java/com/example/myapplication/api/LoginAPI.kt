package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.*

var globalApiKey : String = ""

interface LoginAPI {

    @POST("api-token-auth/")
    fun getLoginToken(@Body userInfo: APIUserInfo): Call<APIToken>

}