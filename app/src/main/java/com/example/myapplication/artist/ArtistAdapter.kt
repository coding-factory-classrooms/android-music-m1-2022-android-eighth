package com.example.myapplication.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Artist
import com.example.myapplication.databinding.ItemArtistBinding


class ArtistAdapter(private var artists:List<Artist>)
    :RecyclerView.Adapter<ArtistAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtistBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = artists[position]
        with(holder.binding){
            artistNameView.text=artist.name

        }
    }

    override fun getItemCount(): Int = artists.size


    fun updateDataSet(artists: List<Artist>) {
        this.artists = artists
        notifyDataSetChanged()
    }

}