package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.login.LoginViewModel
import androidx.navigation.fragment.findNavController
import com.example.myapplication.login.LoginFragmentDirections

class HomeFragment : Fragment() {
    val viewModel : LoginViewModel by viewModels()

    lateinit var binding: FragmentHomeBinding

    // afficher la liste des playlists et sur un clique emmene sur un ecran different pour la gestion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playlistButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPlaylistFragment()
            findNavController().navigate(action)
        }
    }



}