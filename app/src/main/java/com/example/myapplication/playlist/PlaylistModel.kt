package com.example.myapplication.playlist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.Song
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity(tableName = "playlist")
data class Playlist(@PrimaryKey @ColumnInfo(name = "playlist_name") var name : String): Parcelable


@Entity(tableName = "PlaylistArtist")
data class PlaylistArtist(@PrimaryKey(autoGenerate = true) @ColumnInfo(name ="id") var id : Int?,
                          @ColumnInfo(name="Playlist") var PlayListName : String,
                          @ColumnInfo(name = "ArtistId") var ArtistId : Int)