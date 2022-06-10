package com.example.myapplication.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.HomeFragment
import com.example.myapplication.HomeFragmentDirections
import com.example.myapplication.api.APIArtist
import com.example.myapplication.databinding.ItemArtistBinding
import com.squareup.picasso.Picasso


class ArtistAdapter(private var artists:List<APIArtist>)
    :RecyclerView.Adapter<ArtistAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root)

    val albumlivedata = MutableLiveData<APIArtist>(

    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtistBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = artists[position]

        with(holder.binding){
            artistNameView.text=artist.name
            imageView.setOnClickListener(){
                albumlivedata.value= artist
            }
            Picasso.get().load(artist.album_cover_url)
                .into(imageView)

        }
    }

    override fun getItemCount(): Int = artists.size


    fun updateDataSet(artists: List<APIArtist>) {
        this.artists = artists
        notifyDataSetChanged()
    }




}