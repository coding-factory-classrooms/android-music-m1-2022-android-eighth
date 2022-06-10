package com.example.myapplication.artist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Artist
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityArtistListBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemArtistBinding

class ArtistListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityArtistListBinding
    private lateinit var adapter: ArtistAdapter

    private val artists = listOf(
        Artist(5,"John Cena", "Rap"),
        Artist(6,"Ouga Bouga", "Rap"),
        Artist(5,"John Cena", "Rap"),
        Artist(6,"Ouga Bouga", "Rap"),
        Artist(5,"John Cena", "Rap"),
        Artist(6,"Ouga Bouga", "Rap"),
        Artist(5,"John Cena", "Rap"),
        Artist(6,"Ouga Bouga", "Rap"),
        Artist(5,"John Cena", "Rap"),
        Artist(6,"Ouga Bouga", "Rap"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArtistAdapter(artists)

        binding.ArtistsRecyclerView.adapter = adapter
        binding.ArtistsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}