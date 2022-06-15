package com.example.myapplication.playlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.Song
import com.example.myapplication.api.*
import com.example.myapplication.artist.ArtistAdapter
import com.example.myapplication.databinding.ItemArtistBinding
import com.example.myapplication.databinding.ItemPlaylistBinding
import com.example.myapplication.globalSongList
import com.squareup.picasso.Picasso
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistAdapter(private var playlist:List<Playlist>)
    :RecyclerView.Adapter<PlaylistAdapter.ViewHolder>(){
        class ViewHolder(val binding : ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    val playlistLiveData = MutableLiveData<Playlist>()
    var Allalbums = MutableLiveData<List<APIArtist>>()
    val TAG = "PLAYLIST_ADAPTER"


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(inflater,parent,false)
        return PlaylistAdapter.ViewHolder(binding)
        }

    override fun onBindViewHolder(holder: PlaylistAdapter.ViewHolder, position: Int) {
        val playlist  = playlist[position]
        Log.i(TAG,"JE SUIS LA POSITION  {$playlist}")
        with(holder.binding){
            playlistTextView.text= playlist.name
            deleteView.setOnClickListener{
                App.db.playlistDao().deletePlayList(playlist)
                notifyDataSetChanged()
            }

            playListItemView.setOnClickListener(){
                var idsList = App.db.playlistDao().getArtists(playlist.name)
                if(idsList!=null){
                    loadSongsToGlobalList(idsList)
                    playlistLiveData.value= playlist
                }

            }
        }
    }

    override fun getItemCount(): Int = playlist.count()

    fun updateDataSet(playlist: List<Playlist>) {
        this.playlist = playlist
        Log.i(TAG, "Coucou je suis la {$playlist}")
        notifyDataSetChanged()
    }

    fun loadSongsToGlobalList(idsList:List<Int>){
        globalSongList = listOf()
        val artistLiveData = MutableLiveData<List<APIArtist>>()
        val call = ServiceBuilder.buildService(ArtistAPI::class.java).GetArtistsList("Token $globalApiKey")
        call.enqueue(object : Callback<List<APIArtist>> {
            override fun onResponse(call: Call<List<APIArtist>>, response: Response<List<APIArtist>>) {
                val apiArtists = response.body()
                Log.d("Playlist Info", "onResponse: API MOVIES : $apiArtists ")
                artistLiveData.value=apiArtists!!
            }
            override fun onFailure(call: Call<List<APIArtist>>, t: Throwable) {
                Log.e("Playlist Info", "onFailure: ERROR : ", t)
            }
        })
        var list = artistLiveData.value

        Log.d("newList", "loadSongsToGlobalList: $list")

        var newList = list?.filter { artist ->artist.id in idsList }

        Log.d("newList", "loadSongsToGlobalList: $newList")
        Allalbums.value=newList
        Log.d(TAG, "List: $newList ")

        if(newList!=null){
            // Get all songs of those artists
            for (idArtist in newList){
                val SongsLiveData = MutableLiveData<List<Song>>()
                val call = ServiceBuilder.buildService(SongAPI::class.java).GetSongsList("Token $globalApiKey")
                call.enqueue(object : Callback<List<APISong>>{
                    override fun onResponse(call: Call<List<APISong>>, response: Response<List<APISong>>) {
                        val apiSongs = response.body()
                        Log.d("Songs get", "API Songs = $apiSongs ")
                        SongsLiveData.value= mapApiSongstoSongs(apiSongs!!)
                    }

                    override fun onFailure(call: Call<List<APISong>>, t: Throwable) {
                        Log.e("Songs get", "onFailure: ", t)
                    }

                })
                var newSongList = SongsLiveData.value?.filter { song -> song.artistId==idArtist.id }
                if(newSongList!=null){
                    globalSongList= globalSongList+newSongList
                }

            }
        }



    }
}
