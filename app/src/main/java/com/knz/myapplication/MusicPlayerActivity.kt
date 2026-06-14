package com.knz.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar: SeekBar
    private lateinit var textCurrentTime: TextView
    private lateinit var textTotalTime: TextView
    private lateinit var textSongTitle: TextView
    private lateinit var imageViewCover: ImageView
    private lateinit var buttonPlay: ImageView
    private lateinit var buttonPause: ImageView
    private lateinit var buttonStop: ImageView

    private var currentAudioResId: Int = 0
    private val handler = Handler(Looper.getMainLooper())

    private val updateSeekBar = object : Runnable {
        override fun run() {
            if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
                seekBar.progress = mediaPlayer.currentPosition
                textCurrentTime.text = formatTime(mediaPlayer.currentPosition)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_music_player)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentAudioResId = intent.getIntExtra("SONG_RES_ID", R.raw.song1)
        val songTitle = intent.getStringExtra("SONG_TITLE") ?: "Holochaust - Valley Of Misery"
        val songImageResId = intent.getIntExtra("SONG_IMAGE_RES_ID", R.drawable.song1)

        initViews()

        textSongTitle.text = songTitle
        imageViewCover.setImageResource(songImageResId)

        setupMediaPlayer()
        setupButtonListeners()
        setupSeekBarListener()

        autoStartMusic()
    }

    private fun initViews() {
        seekBar = findViewById(R.id.seekBar)
        textCurrentTime = findViewById(R.id.textCurrentTime)
        textTotalTime = findViewById(R.id.textTotalTime)
        textSongTitle = findViewById(R.id.textSongTitle)
        imageViewCover = findViewById(R.id.imageView)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)
        buttonStop = findViewById(R.id.buttonStop)
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, currentAudioResId)
        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            textTotalTime.text = formatTime(mediaPlayer.duration)
        }
    }

    private fun autoStartMusic() {
        mediaPlayer.start()
        handler.post(updateSeekBar)
    }

    private fun setupButtonListeners() {
        buttonPlay.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                handler.removeCallbacks(updateSeekBar)
                handler.post(updateSeekBar)
            }
        }

        buttonPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        buttonStop.setOnClickListener {
            if (::mediaPlayer.isInitialized) {
                mediaPlayer.stop()
                mediaPlayer.release()

                mediaPlayer = MediaPlayer.create(this, currentAudioResId)
                seekBar.progress = 0
                textCurrentTime.text = "0:00"
                textTotalTime.text = formatTime(mediaPlayer.duration)
                handler.removeCallbacks(updateSeekBar)
            }
        }
    }

    private fun setupSeekBarListener() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                    textCurrentTime.text = formatTime(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateSeekBar)
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}