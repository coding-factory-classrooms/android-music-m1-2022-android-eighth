package com.example.myapplication.playlist;

import androidx.room.*


@Dao
interface PlaylistDao {
    @Query("SELECT * FROM `playlist`")
    fun getAllPlaylist(): List<Playlist>

    @Insert
    fun Insert(playlist: Playlist)

    @Query("SELECT count(*) FROM `playlist`")
    fun getTotalOfPlaylists():Int

    @Delete
    fun deletePlayList(playlist: Playlist)

    @Query("SELECT * FROM `playlist` ORDER BY playlist_name DESC LIMIT 1")
    fun getLastPlaylist():Playlist

    @Query("SELECT ArtistId FROM `PlaylistArtist` WHERE Playlist LIKE :playListName")
    fun getArtists(playListName:String) : List<Int>

    @Insert
    fun InsertAlbumtoPlayList(playlistArtist: PlaylistArtist)

    @Query("SELECT * FROM `PlaylistArtist` WHERE Playlist LIKE :playListName AND ArtistId LIKE :id" )
    fun GetPlayListArtist(playListName: String,id:Int):List<PlaylistArtist>

    @Query("SELECT * FROM `PlaylistArtist` WHERE Playlist LIKE :playListName " )
    fun GetPlayListArtistByPlayList(playListName: String):List<PlaylistArtist>

    @Delete
    fun DeleteAlbumFromPlaylist(playlistArtist: PlaylistArtist)

}
