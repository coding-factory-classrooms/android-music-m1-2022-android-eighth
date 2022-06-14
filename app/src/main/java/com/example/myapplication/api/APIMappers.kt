package com.example.myapplication.api

import android.util.Log
import com.example.myapplication.Song

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "GetArtistByID"
 fun getArtistById(id:Int):APIArtist{
    var apiArtist : APIArtist = APIArtist(1,"","","")
    val call = ServiceBuilder.buildService(ArtistAPI::class.java).GetArtistById("Token $globalApiKey",id)
    call.enqueue(object : Callback<APIArtist> {
        override fun onResponse(call: Call<APIArtist>, response: Response<APIArtist>) {
            val responsArtist = response.body()
            apiArtist = responsArtist!!
            Log.d(TAG, "onResponse: $apiArtist")
        }

        override fun onFailure(call: Call<APIArtist>, t: Throwable) {
            Log.e(TAG, "onFailure : getArtist By ID",t )
        }

    }
    )
     return apiArtist
 }

fun mapApiSongstoSongs(apiSong: List<APISong>):List<Song>{
    val list = mutableListOf<Song>()
    for(api in apiSong){
        list.add(mapApiSongtoSong(api))
    }
    return list
}

fun mapApiSongtoSong(apiSong: APISong)
    :Song {
    return Song(
        id = apiSong.id,
        name = apiSong.name,
        file_url = apiSong.file,
        duration = apiSong.duration,
        created_at = apiSong.created_at,
        artistId = apiSong.artist
    )
}