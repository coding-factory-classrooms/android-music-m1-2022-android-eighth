package com.example.myapplication.PlayListInfo.PlayListArtists

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.PlayListInfo.CurrentPlayListSelected
import com.example.myapplication.R
import com.example.myapplication.api.APIArtist
import com.example.myapplication.artist.ArtistAdapter
import com.example.myapplication.databinding.ItemArtistBinding
import com.example.myapplication.databinding.ItemArtistPlaylistBinding
import com.example.myapplication.playlist.Playlist
import com.example.myapplication.playlist.PlaylistArtist
import com.squareup.picasso.Picasso

class AdapterAddRemove(private var artists:List<APIArtist>)
    : RecyclerView.Adapter<AdapterAddRemove.ViewHolder>() {

    class ViewHolder(val binding: ItemArtistPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    val albumlivedata = MutableLiveData<APIArtist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtistPlaylistBinding.inflate(inflater,parent,false)
        return AdapterAddRemove.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = artists[position]

        with(holder.binding){
            artistnameview.text=artist.name
            Picasso.get().load(artist.album_cover_url)
                .into(imageView)
            addRemoveButton.setOnClickListener{
                //addRemoveArtist(artist)
                albumlivedata.value= artist
                updateDataSet(artists)
            }
            var b = App.db.playlistDao().GetPlayListArtist(CurrentPlayListSelected.name,artist.id).isEmpty()
            if(b){
                addRemoveButton.setImageResource(R.drawable.ic_baseline_add_24)
            }else{
                addRemoveButton.setImageResource(R.drawable.ic_baseline_delete_24)
            }
        }
    }

    override fun getItemCount(): Int = artists.size

    fun updateDataSet(artists: List<APIArtist>) {
        this.artists = artists
        notifyDataSetChanged()
    }

    fun addRemoveArtist(album: APIArtist){
        //Check If Exist
        if(CheckIfExist(album).isEmpty()) {
            App.db.playlistDao().InsertAlbumtoPlayList(
                playlistArtist = PlaylistArtist(null,
                CurrentPlayListSelected!!.name,
                album.id
            )
            )
        }else{
            //remove it
            for(PlayListArtist in CheckIfExist(album)){
                App.db.playlistDao().DeleteAlbumFromPlaylist(PlayListArtist)
            }

        }
    }

    fun CheckIfExist(album:APIArtist):List<PlaylistArtist>{
        return App.db.playlistDao().GetPlayListArtist(CurrentPlayListSelected.name,album.id)
    }

}