package com.example.myapplication.artist

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "ArtistListViewModel"

class ArtistListViewModel : ViewModel() {
    private val artistLiveData = MutableLiveData<List<APIArtist>>()

    fun getArtistLiveData(): LiveData<List<APIArtist>> = artistLiveData


    fun loadArtists(){



        val call = ServiceBuilder.buildService(ArtistAPI::class.java).GetArtistsList("Token $globalApiKey")
        call.enqueue(object :  Callback<List<APIArtist>> {
            override fun onResponse(call: Call<List<APIArtist>>, response: Response<List<APIArtist>>) {
                val apiArtists = response.body()
                Log.d(TAG, "onResponse: API MOVIES : $apiArtists ")
                artistLiveData.value=apiArtists!!
            }

            override fun onFailure(call: Call<List<APIArtist>>, t: Throwable) {
                Log.e(TAG, "onFailure: ERROR : ", t)
            }

        })
    }


}