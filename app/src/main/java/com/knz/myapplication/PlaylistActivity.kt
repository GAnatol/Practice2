package com.knz.myapplication

import android.content.Intent
import android.net.Uri
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
            PlaylistListItem(R.drawable.snake,"Metallica - The Unforgiven", "https://music.yandex.ru/album/4766/track/57711?utm_source=web&utm_medium=copy_link"),
            PlaylistListItem(R.drawable.dsb,"Journey - Don't Stop Believin'", "https://music.yandex.ru/album/121277/track/1084275?utm_source=web&utm_medium=copy_link"),
            PlaylistListItem(R.drawable.sr4,"Haddaway - What Is Love", "https://music.yandex.ru/album/357676/track/3401738?utm_source=web&utm_medium=copy_link"),
            PlaylistListItem(R.drawable.thepolice,"The Police - Every Breath You Take", "https://music.yandex.ru/album/7315401/track/27965?utm_source=web&utm_medium=copy_link"),
            PlaylistListItem(R.drawable.nikki,"Estrak - Belt", "https://music.yandex.ru/album/39086250/track/144935121?utm_source=web&utm_medium=copy_link"),
            PlaylistListItem(R.drawable.clams,"Clams Casino - All I Need", "https://music.yandex.ru/album/39944460/track/146638213?utm_source=web&utm_medium=copy_link")
        )

        val adapter = PlaylistAdapterList(this, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = items[position]

            when (selectedItem.action) {
                "https://music.yandex.ru/album/4766/track/57711?utm_source=web&utm_medium=copy_link" -> {
                    Toast.makeText(this, "Первый элемент списка", Toast.LENGTH_SHORT).show()
                }
                "https://music.yandex.ru/album/121277/track/1084275?utm_source=web&utm_medium=copy_link" -> {
                    Toast.makeText(this, "Второй элемент списка", Toast.LENGTH_SHORT).show()
                }
                "https://music.yandex.ru/album/11946076/track/70621383?utm_source=web&utm_medium=copy_link" -> {
                    Toast.makeText(this, "Третий элемент списка", Toast.LENGTH_SHORT).show()
                }
                "https://music.yandex.ru/album/7315401/track/27965?utm_source=web&utm_medium=copy_link" -> {
                    Toast.makeText(this, "Четвёртый элемент списка", Toast.LENGTH_SHORT).show()
                }
                "https://music.yandex.ru/album/39086250/track/144935121?utm_source=web&utm_medium=copy_link" -> {
                    Toast.makeText(this, "Пятый элемент списка", Toast.LENGTH_SHORT).show()
                }
                "https://music.yandex.ru/album/39944460/track/146638213?utm_source=web&utm_medium=copy_link" -> {
                    Toast.makeText(this, "Шестой элемент списка", Toast.LENGTH_SHORT).show()
                }
            }

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(selectedItem.action)
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Не удалось открыть ссылку", Toast.LENGTH_SHORT).show()
            }
        }


    }
}