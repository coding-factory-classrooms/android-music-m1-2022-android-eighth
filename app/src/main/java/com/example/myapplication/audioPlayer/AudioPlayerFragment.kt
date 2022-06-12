package com.example.myapplication.audioPlayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.Song
import com.example.myapplication.api.APIArtist
import com.example.myapplication.databinding.FragmentAudioPlayerBinding
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class AudioPlayerFragment : Fragment() {
    lateinit var song : Song
    lateinit var album : APIArtist
    lateinit var Songs:List<Song>
    var CurrentIndex by Delegates.notNull<Int>()
    private lateinit var binding: FragmentAudioPlayerBinding
    lateinit var mediaPlayer:MediaPlayer
    private val args : AudioPlayerFragmentArgs by navArgs()
    lateinit var runnable: Runnable

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
        mediaPlayer.pause() // can change later
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var handler: Handler = Handler()


        song = args.song
        album = args.album
        Songs = args.listOfSongs.toList()
        CurrentIndex = Songs.indexOf(song)

        binding.TotalTimeView.text = getDisplayedTime(song.duration)
        binding.SongTitleView.text=song.name
        binding.SongTitleView.isSelected
        mediaPlayer = MediaPlayer()
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
        playMusic()

        // Change seekbar position whenever mediaplayer's position changes
        binding.seekBarView.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, fromUser: Boolean) {
                if(fromUser && mediaPlayer!=null){
                    mediaPlayer.seekTo(p1*1000)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // do nothing
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // do nothing
            }

        })


        runnable = Runnable {
            run(){
                if(mediaPlayer!=null){
                    binding.seekBarView.progress = mediaPlayer.currentPosition/1000
                    binding.CurrentTimeView.text=convertToMMSS("${mediaPlayer.currentPosition}")
                    if(mediaPlayer.isPlaying){
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

    private fun playMusic() {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(song.file_url)
        mediaPlayer.prepare()
        binding.seekBarView.progress = 0
        binding.seekBarView.max=song.duration

        mediaPlayer.start()
    }



    private fun pausePlayMusic(){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }else{
            mediaPlayer.start()
        }
    }

    private fun playNextSong(){
        if(CurrentIndex<Songs.size-1){
            song=Songs.get(CurrentIndex+1)
            binding.TotalTimeView.text = getDisplayedTime(song.duration)
            binding.SongTitleView.text=song.name
            binding.SongTitleView.isSelected
            CurrentIndex += 1
            playMusic()
        }
    }

    private fun playPreviousSong(){
        if(CurrentIndex>0){
            song=Songs.get(CurrentIndex-1)
            binding.TotalTimeView.text = getDisplayedTime(song.duration)
            binding.SongTitleView.text=song.name
            binding.SongTitleView.isSelected
            CurrentIndex -= 1
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

fun getDisplayedTimeFromMS(duration:Int) : String{
    var minutes = (duration % 3600) / 60000
    var seconds = (duration % 60000)
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