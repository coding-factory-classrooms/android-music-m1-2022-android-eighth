package com.example.myapplication.playlist;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM `playlist`")
    fun getAllPlaylist(): List<Playlist>
}
