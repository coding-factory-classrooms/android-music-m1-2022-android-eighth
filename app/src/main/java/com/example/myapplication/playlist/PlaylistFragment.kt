package com.example.myapplication.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.App
import com.example.myapplication.HomeFragmentDirections
import com.example.myapplication.Song
import com.example.myapplication.api.APIArtist
import com.example.myapplication.artist.ArtistAdapter
import com.example.myapplication.databinding.FragmentPlaylistBinding
import com.example.myapplication.globalSelectedPlaylist

// afficher le detail d'une playlist a partir d'ici ?

class PlaylistFragment: Fragment(){

    val TAG = "PLAYLIST_FRAGMENT"

    lateinit var playlist: Song

    lateinit var binding: FragmentPlaylistBinding
    private val model : PlaylistViewModel by viewModels()
    private lateinit var adapter: PlaylistAdapter
    private lateinit var Allalbums: List<APIArtist>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getPlaylistLiveData().observe(viewLifecycleOwner, Observer{ playlist -> updatePlaylist(playlist!! )})
        model.getPlaylistLiveData().observe(viewLifecycleOwner, Observer{ playlist -> updatePlaylist(playlist!! )})
        adapter = PlaylistAdapter(listOf())

        adapter.playlistLiveData.observe(viewLifecycleOwner, Observer { Playlist -> navigateToPlayListInfo(Playlist) })

        binding.playlistRecyclerView.adapter = adapter
        binding.playlistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.AddPlayListButton.setOnClickListener{
            var last = 0
            if(App.db.playlistDao().getLastPlaylist()!=null){
                 last = App.db.playlistDao().getLastPlaylist().name.last().toString().toInt()
            }else{
                 last=0
            }

            val Total = last+1
            var p : Playlist = Playlist("MyPlayList$Total")

            addPlayList(p)
        }
        model.load()

    }

    private fun navigateToPlayListInfo(playlist: Playlist) {
        globalSelectedPlaylist=playlist
        val action= PlaylistFragmentDirections.actionPlaylistFragmentToPlayListInfoFragment(playlist)
        findNavController().navigate(action)
    }

    private fun updatePlaylist(playlist: List<Playlist>) {

            Log.i(TAG, "Update done bg {$playlist}")
            adapter.updateDataSet(playlist)
    }

    private fun addPlayList(playlist: Playlist){
        model.addPlaylist(playlist)
        model.load()
    }

}