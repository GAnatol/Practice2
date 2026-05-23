package com.knz.myapplication

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.knz.myapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })

        val textView = findViewById<TextView>(R.id.expandable_text)
        val btnExp = findViewById<ImageButton>(R.id.expand_collapse_btn)
        var isExpanded = false

        btnExp.setOnClickListener {
            TransitionManager.beginDelayedTransition(textView.parent as ViewGroup)
            if (isExpanded) {
                textView.maxLines = 3
                btnExp.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                textView.maxLines = Int.MAX_VALUE
                btnExp.setImageResource(android.R.drawable.arrow_up_float)
            }
            isExpanded = !isExpanded
        }

        val webView = binding.wvMain
        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()

                view?.loadUrl(url)

                return true
            }
        }
        binding.wvMain.loadUrl("https://www.youtube.com/watch?v=LXb3EKWsInQ")


        val btn : Button = findViewById(R.id.btnChoose)
        val tvChos : TextView = findViewById(R.id.tvChoose)

        btn.setOnClickListener {
            val result = StringBuilder()
            if (binding.chk1.isChecked && binding.chk2.isChecked && binding.chk3.isChecked) {
                tvChos.text = "выбраны все флажки"
            } else if (!binding.chk1.isChecked && !binding.chk2.isChecked && !binding.chk3.isChecked){
                tvChos.text = "ничего не выбрано"
            } else if (binding.chk1.isChecked) {
                tvChos.text = "верно, это ${binding.chk1.text}"
            } else {
                tvChos.text = "неверно"
            }
        }


        val btnSchedule : Button = findViewById(R.id.btn2)

        btnSchedule.setOnClickListener {
            intent = Intent(this, TimetableActivity::class.java)
            startActivity(intent)
        }

        val btnSecundomer : Button = findViewById(R.id.btn3)

        btnSecundomer.setOnClickListener {
            intent = Intent(this, StopwatchActivity::class.java)
            startActivity(intent)
        }

        val btnPlaylist : Button = findViewById(R.id.btn4)

        btnPlaylist.setOnClickListener {
            intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        }

        val btnRView : Button = findViewById(R.id.btnRV)

        btnRView.setOnClickListener {
            intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }
    }
}