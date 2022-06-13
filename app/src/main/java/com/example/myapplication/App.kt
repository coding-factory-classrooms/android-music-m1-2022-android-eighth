package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.database.AppDatabase

class App : Application() {

    companion object{
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

         db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Music.db"
        ).allowMainThreadQueries()
            .build()

        db.playlistDao().getAllPlaylist()
    }
}