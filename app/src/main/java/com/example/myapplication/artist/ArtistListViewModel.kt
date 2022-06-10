package com.example.myapplication.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Artist
import com.example.myapplication.api.LoginAPI
import com.example.myapplication.login.LoginViewModel


class ArtistListViewModel : ViewModel() {
    private val artistLiveData: MutableLiveData<List<Artist>>()

    fun getArtistLiveData(): LiveData<List<Artist>> = artistLiveData



    fun loadArtists(){
        LoginViewModel.
    }
}