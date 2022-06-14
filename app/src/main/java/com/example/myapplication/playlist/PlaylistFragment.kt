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
import com.example.myapplication.HomeFragmentDirections
import com.example.myapplication.Song
import com.example.myapplication.api.APIArtist
import com.example.myapplication.artist.ArtistAdapter
import com.example.myapplication.databinding.FragmentPlaylistBinding

// afficher le detail d'une playlist a partir d'ici ?

class PlaylistFragment: Fragment(){

    val TAG = "PLAYLIST_FRAGMENT"

    lateinit var playlist: Song

    lateinit var binding: FragmentPlaylistBinding
    private val model : PlaylistViewModel by viewModels()
    private lateinit var adapter: PlaylistAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.createPlaylistButton.setOnClickListener{
            val action = PlaylistFragmentDirections.actionPlaylistFragmentToFormNewPlaylistFragment()
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)

        model.getPlaylistLiveData().observe(viewLifecycleOwner, Observer{ playlist -> updatePlaylist(playlist!! )})

        adapter = PlaylistAdapter(listOf())

        /*      --  redirection vers la liste des sons de la playlist --

        adapter.playlistLiveData.observe(viewLifecycleOwner, Observer { album -> navigateToSongsList(album) })
         */
        binding.playlistRecyclerView.adapter = adapter
        binding.playlistRecyclerView.layoutManager = LinearLayoutManager(context)

        model.load()

    }

    private fun updatePlaylist(playlist: List<Playlist>) {

            Log.i(TAG, "Update done bg {$playlist}")
            adapter.updateDataSet(playlist)
    }
}