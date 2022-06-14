package com.example.myapplication.PlayListInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.myapplication.Song
import com.example.myapplication.databinding.FragmentPlayListInfoBinding
import com.example.myapplication.globalSongList
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.App
import com.example.myapplication.globalSelectedPlaylist
import com.example.myapplication.playlist.Playlist

var CurrentPlayListSelected = Playlist("null")

class PlayListInfoFragment : Fragment() {

    lateinit var binding: FragmentPlayListInfoBinding
    private val args : PlayListInfoFragmentArgs by navArgs()
    private lateinit var adapter : PlayListInfoAdapter
    lateinit var songforArg : Song
    private val model : PlayListInfoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentPlayListInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var playlist = args.playList
        CurrentPlayListSelected=playlist


        adapter = PlayListInfoAdapter(listOf())

        model.getSongsLiveData().observe(viewLifecycleOwner,Observer{songs ->
            updateSongs(songs!!)
        })

        binding.infoRecyclerView.adapter=adapter
        binding.infoRecyclerView.layoutManager = LinearLayoutManager(context)

        adapter.SongLiveData.observe(viewLifecycleOwner, Observer {
                song -> songforArg=song
                navigateToAudioPlayer(songforArg, globalSongList)
        })





        binding.AddButtonView.setOnClickListener{
            navigateToAddOrRemoveArtist(playlist)
        }


        binding.NameView.text=playlist.name

        model.loadSongs()

    }

    private fun updateSongs(songs : List<Song>){
        // TO FILTER OUR LIST TO ONE ARTIST ID
        var artistPlaylistList= App.db.playlistDao().GetPlayListArtistByPlayList(globalSelectedPlaylist.name)
        var artistsIds : List<Int> = listOf()
        for(s in artistPlaylistList){
            artistsIds=artistsIds+s.ArtistId
        }

        Log.d("artistPlaylistList", "updateSongs: $artistPlaylistList $artistsIds")
        var newSongsList = songs.filter { song -> song.artistId in artistsIds }
//        ListOfSongs = newSongsList
        globalSongList=newSongsList
        Log.d("LOGDLOGD", "updateSongs: $globalSongList")
        adapter.updateDataSet(newSongsList)
    }



    private fun navigateToAddOrRemoveArtist(playlist:Playlist){

        val action =PlayListInfoFragmentDirections.actionPlayListInfoFragmentToPlayListAddRemoveArtistFragment(playlist)

        if (action != null) {
            findNavController().navigate(action)
        }
    }

    private fun navigateToAudioPlayer(song: Song, ListOfSongs: List<Song>?){
        val action = ListOfSongs?.let {
            PlayListInfoFragmentDirections.actionPlayListInfoFragmentToAudioPlayerForPlayListFragment(song,it.toTypedArray())
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }
}