package com.knz.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_playlist)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })

        val listView : ListView = findViewById(R.id.lvMain)

        val items = listOf(
            PlaylistListItem(R.drawable.song1, "Holochaust - Valley Of Misery", R.raw.song1),
            PlaylistListItem(R.drawable.song2, "Orange Sector - Farben", R.raw.song2),
            PlaylistListItem(R.drawable.song3, "Deluxe Schnabel - Ein Blutzeuge Aus Frankreich", R.raw.song3),
            PlaylistListItem(R.drawable.song4, "Black Sabbath - In Memory", R.raw.song4),
            PlaylistListItem(R.drawable.song5, "Type O Negative - IYDKMIGTHTKY (Gimme That)", R.raw.song5)
        )

        val adapter = PlaylistAdapterList(this, items)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = items[position]
            Toast.makeText(this, "Воспроизведение: ${selectedItem.text}", Toast.LENGTH_SHORT).show()

            val audioResId = selectedItem.audioResId

            val intent = Intent(this, MusicPlayerActivity::class.java).apply {
                putExtra("SONG_RES_ID", audioResId)
                putExtra("SONG_TITLE", selectedItem.text)
                putExtra("SONG_IMAGE_RES_ID", selectedItem.imageR)
            }

            startActivity(intent)
        }
    }
}