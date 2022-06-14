package com.example.myapplication.audioPlayer

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.*
import com.example.myapplication.api.APIArtist
import com.example.myapplication.databinding.FragmentAudioPlayerBinding
import com.example.myapplication.song.SongListViewModel
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception

import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class AudioPlayerFragment : Fragment() {
    lateinit var song : Song
    lateinit var album : APIArtist
    lateinit var Songs:List<Song>
    private lateinit var binding: FragmentAudioPlayerBinding
    private val args : AudioPlayerFragmentArgs by navArgs()
    lateinit var runnable: Runnable

    private val model : SongListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=  FragmentAudioPlayerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //globalMediaPlayer.pause() // can change later
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var handler: Handler = Handler()
        var sameSong: Boolean = false
        song = args.song
        album = args.album
        Songs = args.listOfSongs.toList()
        globalSongList = Songs
        if(globalCurrentIndex== globalSongList.indexOf(song)){
            sameSong = true
        }
        globalCurrentIndex = globalSongList.indexOf(song)

        binding.TotalTimeView.text = getDisplayedTime(song.duration)
        binding.SongTitleView.text=song.name
        binding.SongTitleView.isSelected
        Picasso.get().load(album.album_cover_url)
            .into(binding.imageView3)
        binding.PausePlayButtonView.setOnClickListener(){
            pausePlayMusic()
        }
        binding.PreviousButtonView.setOnClickListener(){
            playPreviousSong()
        }
        binding.NextButtonView.setOnClickListener(){

            playNextSong()
        }
        // auto play music
        if(sameSong){

        }else{
            playMusic()
        }


        // Change seekbar position whenever mediaplayer's position changes
        binding.seekBarView.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, fromUser: Boolean) {
                if(fromUser && globalMediaPlayer!=null){
                    globalMediaPlayer.seekTo(p1*1000)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                // do nothing
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                // do nothing
            }

        })

        //Auto Play Next Song on Media Player Completion
        globalMediaPlayer.setOnCompletionListener{
            playNextSong()
        }


        runnable = Runnable {
            run(){
                if(globalMediaPlayer!=null){
                    binding.seekBarView.progress = globalMediaPlayer.currentPosition/1000
                    binding.CurrentTimeView.text=convertToMMSS("${globalMediaPlayer.currentPosition}")
                    if(globalMediaPlayer.isPlaying){
                        binding.PausePlayButtonView.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                    }else{
                        binding.PausePlayButtonView.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                    }
                }
            }
            handler.postDelayed(runnable,100)
        }
        runnable.run()



    }


    // _____________________My Fuctions_____________________
    private fun CheckSkip(){
        if(globalCurrentIndex==globalSongList.size-1){
            binding.NextButtonView.setImageResource(R.drawable.ic_baseline_skip_next_24_grey)
        }else{
            binding.NextButtonView.setImageResource(R.drawable.ic_baseline_skip_next_24)
        }
        if(globalCurrentIndex==0){
            binding.PreviousButtonView.setImageResource(R.drawable.ic_baseline_skip_previous_24_grey)
        }else{
            binding.PreviousButtonView.setImageResource(R.drawable.ic_baseline_skip_previous_24)
        }
    }

    private fun playMusic() {

        globalMediaPlayer.reset()

        //Get the cached file
        val fileName=song.file_url.substring(song.file_url.lastIndexOf("/")+1)
        try {

            val songFile = File(requireContext().filesDir, fileName)
            if(songFile.exists()){
                globalMediaPlayer.setDataSource(songFile.path)
            }else{
                // Cache the files !
                model.SaveFile(song.file_url,songFile.path)

                globalMediaPlayer.setDataSource(song.file_url)
            }

        }catch (e: Exception ){
            Log.e("CachingFile", "playMusic: ${e.toString()}", )
        }


        saveNextSongs()
        globalMediaPlayer.prepare()
        binding.seekBarView.progress = 0
        binding.seekBarView.max=song.duration
        CheckSkip()

        globalMediaPlayer.start()
    }

    private fun saveNextSongs(){
        if(globalCurrentIndex<globalSongList.size-1){
            val nextSong = globalSongList.get(globalCurrentIndex+1)
            val fileName=nextSong.file_url.substring(nextSong.file_url.lastIndexOf("/")+1)
            val songFile = File(requireContext().filesDir, fileName)
            if(!songFile.exists()){
                model.SaveFile(nextSong.file_url,songFile.path)
            }
        }
        if(globalCurrentIndex<globalSongList.size-2){
            val nextSong = globalSongList.get(globalCurrentIndex+2)
            val fileName=nextSong.file_url.substring(nextSong.file_url.lastIndexOf("/")+1)
            val songFile = File(requireContext().filesDir, fileName)
            if(!songFile.exists()){
                model.SaveFile(nextSong.file_url,songFile.path)
            }
        }
    }



    private fun pausePlayMusic(){
        if(globalMediaPlayer.isPlaying){
            globalMediaPlayer.pause()
        }else{
            globalMediaPlayer.start()
        }
    }

    private fun playNextSong(){
        if(globalCurrentIndex<globalSongList.size-1){
            song=globalSongList.get(globalCurrentIndex+1)
            binding.TotalTimeView.text = getDisplayedTime(song.duration)
            binding.SongTitleView.text=song.name
            binding.SongTitleView.isSelected
            globalCurrentIndex += 1
            playMusic()
        }
    }

    private fun playPreviousSong(){
        if(globalCurrentIndex>0){
            song=globalSongList.get(globalCurrentIndex-1)
            binding.TotalTimeView.text = getDisplayedTime(song.duration)
            binding.SongTitleView.text=song.name
            binding.SongTitleView.isSelected
            globalCurrentIndex -= 1
            playMusic()
        }
    }


}





fun getDisplayedTime(duration:Int) : String{
    var minutes = (duration % 3600) / 60
    var seconds = (duration % 60)
    var displayedSeconds : String = if(seconds<10){
        "0$seconds"
    }else{
        "$seconds"
    }
    return "$minutes:$displayedSeconds"
}


fun convertToMMSS(duration: String): String? {
    val millis = duration.toLong()
    return java.lang.String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
    )
}