package com.example.myapplication.song


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Song
import com.example.myapplication.api.APIArtist
import com.example.myapplication.databinding.FragmentSongListBinding
import com.squareup.picasso.Picasso


class SongListFragment : Fragment() {

    lateinit var album : APIArtist
    lateinit var songforArg : Song
    var ListOfSongs : List<Song>? = null

    private val args : SongListFragmentArgs by navArgs()
    private val model : SongListViewModel by viewModels()
    private lateinit var binding: FragmentSongListBinding
    private lateinit var adapter : SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  FragmentSongListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SongAdapter(listOf())
        album = args.album

        model.getSongsLiveData().observe(viewLifecycleOwner,Observer{songs ->
            updateSongs(songs!!)
            ListOfSongs = songs!!.filter { song -> song.artistId==album.id }
        })

        binding.SongListRecyclerView.adapter = adapter
        binding.SongListRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.SongLiveData.observe(viewLifecycleOwner, Observer { song -> songforArg=song })
        adapter.SongLiveData.observe(viewLifecycleOwner, Observer { navigateToAudioPlayer(songforArg,album,ListOfSongs) })



        binding.albulNameView.text=album.name
        binding.albulNameView.isSelected
        Picasso.get().load(album.album_cover_url)
            .into(binding.artistImageUrl)
        model.loadSongs()


    }

    private fun updateSongs(songs : List<Song>){
        // TO FILTER OUR LIST TO ONE ARTIST ID
        var newSongsList = songs.filter { song -> song.artistId==album.id }
        ListOfSongs = newSongsList
        adapter.updateDataSet(newSongsList)
    }


    private fun navigateToAudioPlayer(song: Song, album: APIArtist, ListOfSongs: List<Song>?){
        val action = ListOfSongs?.let {
            SongListFragmentDirections.actionSongListFragmentToAudioPlayerFragment(song,album, it.toTypedArray()
            )
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }
}