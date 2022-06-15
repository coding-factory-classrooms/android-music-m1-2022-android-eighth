package com.example.myapplication.PlayListInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.App
import com.example.myapplication.Song
import com.example.myapplication.api.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class PlayListInfoViewModel  : ViewModel() {
    private val artistLiveData = MutableLiveData<List<APIArtist>>()

    fun getArtistLiveData(): LiveData<List<APIArtist>> = artistLiveData

    private val SongsLiveData = MutableLiveData<List<Song>>()
    fun getSongsLiveData(): LiveData<List<Song>> = SongsLiveData



    fun loadSongs(){
        // Check which artists are included in the play List
        var ArtistListIncluded = App.db.playlistDao().
        GetPlayListArtistByPlayList(CurrentPlayListSelected.name)
        Log.d("MyDb", "loadSongs: $ArtistListIncluded")

        val call = ServiceBuilder.buildService(SongAPI::class.java).GetSongsList("Token $globalApiKey")
        call.enqueue(object : Callback<List<APISong>>{
            override fun onResponse(call: Call<List<APISong>>, response: Response<List<APISong>>) {
                val apiSongs = response.body()
                Log.d("MyDb", "API Songs ${apiSongs?.size} = $apiSongs ")
                SongsLiveData.value= mapApiSongstoSongs(apiSongs!!)
            }

            override fun onFailure(call: Call<List<APISong>>, t: Throwable) {
                Log.e("MyDb", "onFailure: ", t)
            }

        })



    }
}