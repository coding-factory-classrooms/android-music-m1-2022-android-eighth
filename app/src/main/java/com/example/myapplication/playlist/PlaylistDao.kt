package com.example.myapplication.playlist;

import androidx.room.Dao;
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query;

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM `playlist`")
    fun getAllPlaylist(): List<Playlist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(playlist: Playlist)
}
