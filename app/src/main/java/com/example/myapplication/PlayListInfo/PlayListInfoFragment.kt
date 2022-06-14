package com.example.myapplication.PlayListInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.myapplication.Song
import com.example.myapplication.databinding.FragmentPlayListInfoBinding
import com.example.myapplication.globalSongList
import com.example.myapplication.song.SongAdapter
import androidx.navigation.fragment.findNavController
import com.example.myapplication.playlist.Playlist
import com.example.myapplication.playlist.PlaylistAdapter

var CurrentPlayListSelected = Playlist("null")

class PlayListInfoFragment : Fragment() {

    lateinit var binding: FragmentPlayListInfoBinding
    private val args : PlayListInfoFragmentArgs by navArgs()
    private lateinit var adapter : PlayListInfoAdapter
    lateinit var songforArg : Song


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
        adapter.SongLiveData.observe(viewLifecycleOwner, Observer { song -> songforArg=song })

        adapter.SongLiveData.observe(viewLifecycleOwner, Observer { navigateToAudioPlayer(songforArg, globalSongList) })

        binding.AddButtonView.setOnClickListener{
            navigateToAddOrRemoveArtist(playlist)
        }


        binding.NameView.text=playlist.name


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