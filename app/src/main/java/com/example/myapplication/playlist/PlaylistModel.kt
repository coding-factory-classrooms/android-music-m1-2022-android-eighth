package com.example.myapplication.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.Song

@Entity(tableName = "playlist")
data class Playlist(@PrimaryKey @ColumnInfo(name = "playlist_name")val name : String)