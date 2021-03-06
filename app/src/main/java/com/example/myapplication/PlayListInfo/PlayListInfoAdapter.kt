package com.example.myapplication.PlayListInfo

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Song
import com.example.myapplication.databinding.ItemSongBinding
import com.example.myapplication.globalCurrentIndex
import com.example.myapplication.globalMediaPlayer
import com.example.myapplication.globalSongList
import com.example.myapplication.song.SongAdapter

class PlayListInfoAdapter(private var songs:List<Song>)
    : RecyclerView.Adapter<PlayListInfoAdapter.ViewHolder>(){
    class ViewHolder (val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){

    }

    val SongLiveData = MutableLiveData<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListInfoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(inflater,parent,false)
        return PlayListInfoAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Song = songs[position]
        with(holder.binding){


            var minutes = (Song.duration % 3600) / 60
            var seconds = Song.duration % 60
            songTitleView.text=Song.name
            durationView.text="$minutes"+"m$seconds"+"s"
            itemIdView.setOnClickListener(){
                SongLiveData.value= Song
            }
            createdAtView.text=Song.created_at.reversed().substring(22).reversed()
            if(!globalSongList.isEmpty() && globalMediaPlayer.isPlaying){
                if(globalCurrentIndex!=600){
                    if(songs[position].name == globalSongList.get(globalCurrentIndex!!).name){
                        // if globalCurrentIndex > 5 Then color globalSongList[globalCurrentIndex - 5]
                        // For no apparent reason . idk why :'''(
                        // please help
                        songTitleView.setTextColor(Color.parseColor("#14b36c"))
                        durationView.setTextColor(Color.parseColor("#14b36c"))
                        createdAtView.setTextColor(Color.parseColor("#14b36c"))
                        textView3.setTextColor(Color.parseColor("#14b36c"))
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = songs.size

    fun updateDataSet(songs : List<Song>){
        this.songs = songs
        notifyDataSetChanged()
    }
}