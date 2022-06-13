package com.example.myapplication.song

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Song
import com.example.myapplication.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "SongListViewModel"


class SongListViewModel : ViewModel() {
    private val SongsLiveData = MutableLiveData<List<Song>>()
    fun getSongsLiveData(): LiveData<List<Song>> = SongsLiveData


    fun loadSongs(){
        val call = ServiceBuilder.buildService(SongAPI::class.java).GetSongsList("Token $globalApiKey")
        call.enqueue(object : Callback<List<APISong>>{
            override fun onResponse(call: Call<List<APISong>>, response: Response<List<APISong>>) {
                val apiSongs = response.body()
                Log.d(TAG, "API Songs = $apiSongs ")
                SongsLiveData.value= mapApiSongstoSongs(apiSongs!!)
            }

            override fun onFailure(call: Call<List<APISong>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }

        }

        )
    }
}