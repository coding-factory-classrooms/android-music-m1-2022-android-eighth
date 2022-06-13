package com.example.myapplication.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.App
import com.example.myapplication.Song
import com.example.myapplication.api.APIArtist

class PlaylistViewModel: ViewModel(){
    // create , update , delete playlist
    // add and delete song from playlist
    private val PlaylistLiveData = MutableLiveData<List<Playlist>>()

    fun getPlaylistLiveData(): LiveData<List<Playlist>> = PlaylistLiveData


    fun load(){
        val playlists = App.db.playlistDao().getAllPlaylist()
        PlaylistLiveData.value = playlists
    }
}