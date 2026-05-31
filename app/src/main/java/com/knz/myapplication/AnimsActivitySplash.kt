package com.knz.myapplication

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AnimsActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anims_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgCat2 : ImageView = findViewById(R.id.catAnim2)
        val anim2 : Animation = AnimationUtils.loadAnimation(this,R.anim.my_anim)
        imgCat2.startAnimation(anim2)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AnimsActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}