package com.example.myapplication.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Artist
import com.example.myapplication.api.APIArtist
import com.example.myapplication.databinding.FragmentArtistListBinding


class ArtistListFragment : Fragment() {

    private val model : ArtistListViewModel by viewModels()
    private lateinit var binding: FragmentArtistListBinding
    private lateinit var adapter: ArtistAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentArtistListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getArtistLiveData().observe(viewLifecycleOwner, Observer{ artists -> updateArtists(artists!! )})

        adapter = ArtistAdapter(listOf())
        binding.ArtistsRecyclerView.adapter = adapter
        binding.ArtistsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        model.loadArtists()

    }

    private fun updateArtists(artists: List<APIArtist>) {

        // TO FILTER OUR LIST TO A SINGLE GENRE, COMMENT THE NEXT LINE IF YOU WANT TO REMOVE THE FILTER
        var newArtistList = artists.filter { apiArtist -> apiArtist.genre_name=="Retro Games" }

        adapter.updateDataSet(newArtistList)
    }
}