package com.example.myapplication.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Song

import com.example.myapplication.databinding.ItemSongBinding

class SongAdapter(private var songs:List<Song>)
    :RecyclerView.Adapter<SongAdapter.ViewHolder>(){
    class ViewHolder (val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Song = songs[position]
        with(holder.binding){
            var minutes = (Song.duration % 3600) / 60
            var seconds = Song.duration % 60
            songTitleView.text=Song.name
            durationView.text="$minutes"+"m$seconds"+"s"
            createdAtView.text=Song.created_at.reversed().substring(22).reversed()
        }
    }

    override fun getItemCount(): Int = songs.size

    fun updateDataSet(songs : List<Song>){
        this.songs = songs
        notifyDataSetChanged()
    }

}