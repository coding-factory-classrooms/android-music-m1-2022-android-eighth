package com.example.myapplication.PlayListInfo.PlayListArtists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.api.APIArtist
import com.example.myapplication.artist.ArtistListViewModel
import com.example.myapplication.databinding.FragmentPlayListAddRemoveArtistBinding
import com.example.myapplication.playlist.PlaylistViewModel


class PlayListAddRemoveArtistFragment : Fragment() {
    lateinit var binding: FragmentPlayListAddRemoveArtistBinding
    private val args : PlayListAddRemoveArtistFragmentArgs by navArgs()
    private lateinit var adapter : AdapterAddRemove
    private val model : ArtistListViewModel by viewModels()
    private val model2 : PlaylistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayListAddRemoveArtistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var playlist = args.playList



        adapter = AdapterAddRemove(listOf())
        adapter.albumlivedata.observe(viewLifecycleOwner, Observer { artist ->
            deleteitem(artist)
        })
        model.getArtistLiveData().observe(viewLifecycleOwner, Observer{ artists -> updateArtists(artists!! )})
        binding.RecyclerViewAddRemove.adapter = adapter
        binding.RecyclerViewAddRemove.layoutManager = LinearLayoutManager(context)


        binding.nameView.text=playlist.name

        model.loadArtists()

    }



    private fun updateArtists(artists: List<APIArtist>) {

        // TO FILTER OUR LIST TO A SINGLE GENRE, COMMENT THE NEXT LINE IF YOU WANT TO REMOVE THE FILTER
        var newArtistList = artists.filter { apiArtist -> apiArtist.genre_name=="Drill" }

        adapter.updateDataSet(newArtistList)
    }

    private fun deleteitem(artist : APIArtist){
        adapter.addRemoveArtist(artist)

    }

}