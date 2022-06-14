package com.example.myapplication


import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.login.LoginViewModel
import java.io.File

var globalMediaPlayer: MediaPlayer = MediaPlayer()
var globalSongList : List<Song> = listOf()
var globalCurrentIndex : Int = 0
var globalCurrentPicasso : String =""


class HomeFragment : Fragment() {

    val viewModel : LoginViewModel by viewModels()

    //Unify Media Player With This code in every fragment page

    var globalSomething : String = ""



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


        binding.ClearCacheView.setOnClickListener{
            try {
                val dir: File = requireContext().filesDir
                if(deleteDir(dir)){
                    Log.d("DELETE", "onViewCreated: Success ${dir.path}")
                    val toast = Toast.makeText(context, "All Songs have been cleared from Cache" , 5)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }else{
                    Log.d("DELETE", "onViewCreated: Failed")
                }

            } catch (e: Exception) {
            }
        }

    }
    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) dir.delete() else {
            false
        }
    }
}

