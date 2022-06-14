package com.example.myapplication.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFormNewPlaylistBinding

class FormNewPlaylistFragment : Fragment() {

    lateinit var binding: FragmentFormNewPlaylistBinding
    private val model : PlaylistViewModel by viewModels()
    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormNewPlaylistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.validatePlaylistName.setOnClickListener{
            val playlistTitle = binding.playlistEditName.text.toString()

            if(playlistTitle.isNotEmpty()){
                val playlist = Playlist(name = playlistTitle)
                model.insertPlaylist(playlist)
                val action = FormNewPlaylistFragmentDirections.actionFormNewPlaylistFragmentToPlaylistFragment()
                findNavController().navigate(action)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}