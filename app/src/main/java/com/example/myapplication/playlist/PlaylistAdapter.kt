package com.example.myapplication.playlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Song
import com.example.myapplication.api.APIArtist
import com.example.myapplication.artist.ArtistAdapter
import com.example.myapplication.databinding.ItemArtistBinding
import com.example.myapplication.databinding.ItemPlaylistBinding
import com.squareup.picasso.Picasso

class PlaylistAdapter(private var playlist:List<Playlist>)
    :RecyclerView.Adapter<PlaylistAdapter.ViewHolder>(){
        class ViewHolder(val binding : ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    val playlistLiveData = MutableLiveData<Playlist>()
    val TAG = "PLAYLIST_ADAPTER"


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(inflater,parent,false)
        return PlaylistAdapter.ViewHolder(binding)
        }

    override fun onBindViewHolder(holder: PlaylistAdapter.ViewHolder, position: Int) {
        val playlist  = playlist[position]
        Log.i(TAG,"JE SUIS LA POSITION  {$playlist}")
        with(holder.binding){
            playlistTextView.text= playlist.name
        }
    }

    override fun getItemCount(): Int = playlist.count()

    fun updateDataSet(playlist: List<Playlist>) {
        this.playlist = playlist
        Log.i(TAG, "Coucou je suis la {$playlist}")
        notifyDataSetChanged()
    }
}
