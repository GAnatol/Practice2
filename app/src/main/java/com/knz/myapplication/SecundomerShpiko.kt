package com.knz.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecundomerShpiko : AppCompatActivity() {
    lateinit var chronometer : Chronometer
    var running : Boolean = false
    var offset : Long = 0
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_secundomer_shpiko)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })

        chronometer = findViewById(R.id.textTime)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnPause = findViewById<Button>(R.id.btnPause)

        if (savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                chronometer.base = savedInstanceState.getLong(BASE_KEY)
                chronometer.start()
            }else setBaseTime()
        }

        btnStart.setOnClickListener {
            if (!running) {
                setBaseTime()
                chronometer.start()
                running = true
            }
        }

        btnPause.setOnClickListener {
            if (running) {
                saveOffset()
                chronometer.stop()
                running = false
            }
        }

        btnReset.setOnClickListener {
            offset = 0
            setBaseTime()
            if (running) {
                chronometer.stop()
                running = false
            }
            chronometer.base = SystemClock.elapsedRealtime()
            offset = 0
        }
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - chronometer.base
    }
    private fun setBaseTime() {
        chronometer.base = SystemClock.elapsedRealtime() - offset
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("offset",offset)
        savedInstanceState.putBoolean("running",running)
        savedInstanceState.putLong("base_key",chronometer.base)
        super.onSaveInstanceState(savedInstanceState)
    }
}