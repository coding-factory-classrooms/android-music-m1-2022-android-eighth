package com.example.myapplication.song

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Song
import com.example.myapplication.api.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception

private const val TAG = "SongListViewModel"


class SongListViewModel : ViewModel() {
    private val SongsLiveData = MutableLiveData<List<Song>>()
    fun getSongsLiveData(): LiveData<List<Song>> = SongsLiveData


    fun loadSongs(){
        val call = ServiceBuilder.buildService(SongAPI::class.java).GetSongsList("Token $globalApiKey")
        call.enqueue(object : Callback<List<APISong>>{
            override fun onResponse(call: Call<List<APISong>>, response: Response<List<APISong>>) {
                val apiSongs = response.body()
                Log.d(TAG, "API Songs = $apiSongs ")
                SongsLiveData.value= mapApiSongstoSongs(apiSongs!!)
            }

            override fun onFailure(call: Call<List<APISong>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }

        }

        )
    }
    // The below code i used to cache a song / Multiple songs
    fun SaveFile(url : String, pathWhereYouWantToSaveFile:String){
        viewModelScope.launch {
            val responseBody = ServiceBuilder.buildService(SongAPI::class.java).fileDownload(url)
            CacheFile(responseBody,pathWhereYouWantToSaveFile)
        }
    }


    private fun CacheFile(body: Response<ResponseBody>, pathWhereYouWantToSaveFile: String) : String {
        Log.d("CacheFiel", "CacheFile:$body,  $pathWhereYouWantToSaveFile ",)
        if (body==null)
            return ""
        var input: InputStream? = null
        try {
            input = body.body()!!.byteStream()
            //val file = File(getCacheDir(), "cacheFileAppeal.srl")
            val fos = FileOutputStream(pathWhereYouWantToSaveFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        }catch (e:Exception){
            Log.e("saveFile",e.toString())
        }
        finally {
            input?.close()
        }
        return ""
    }


}