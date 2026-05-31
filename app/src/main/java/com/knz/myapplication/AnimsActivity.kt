package com.knz.myapplication

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AnimsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anims)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val img : ImageView = findViewById(R.id.sonicAnim)
        img.setBackgroundResource(R.drawable.sonic_animation)
        val frameAnimation = img.background as AnimationDrawable

        frameAnimation.start()

        val imgCat : ImageView = findViewById(R.id.catAnim)
        val anim : Animation = AnimationUtils.loadAnimation(this,R.anim.new_animation)
        imgCat.startAnimation(anim)

        val imgCat2 : ImageView = findViewById(R.id.catAnim2)
        val anim2 : Animation = AnimationUtils.loadAnimation(this,R.anim.my_anim)
        imgCat2.startAnimation(anim2)
    }
}