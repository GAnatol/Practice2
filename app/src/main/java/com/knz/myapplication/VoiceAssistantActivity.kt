package com.knz.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VoiceAssistantActivity : AppCompatActivity() {
    private lateinit var btnRecord: Button
    private lateinit var txtResult: TextView
    private val speechRecognizer: SpeechRecognizer by lazy { SpeechRecognizer.createSpeechRecognizer(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voice_assistant)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRecord = findViewById(R.id.btnRecord)
        txtResult = findViewById(R.id.txtResult)

        setupSpeechRecognizer()

        btnRecord.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecognition()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), 1)
            }
        }
    }

    private fun setupSpeechRecognizer() {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {

            override fun onBeginningOfSpeech() {
                setButtonListeningState()
            }

            override fun onResults(results: Bundle?) {
                results?.let {
                    val matches = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        txtResult.text = matches[0]
                    }
                }
                setButtonIdleState()
            }

            override fun onPartialResults(partialResults: Bundle?) {
                partialResults?.let {
                    val matches = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        txtResult.text = matches[0]
                    }
                }
            }

            override fun onError(error: Int) {
                Toast.makeText(this@VoiceAssistantActivity, "Ошибка распознавания: $error", Toast.LENGTH_SHORT).show()
                setButtonIdleState()
            }

            override fun onEndOfSpeech() {
                setButtonProcessingState()
            }

            override fun onReadyForSpeech(params: Bundle?) {
                setButtonListeningState()
            }

            override fun onRmsChanged(rmsdB: Float) {
            }

            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer.startListening(intent)
    }

    private fun setButtonListeningState() {
        btnRecord.text = "Записать голос..."
        btnRecord.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#E53935"))
    }

    private fun setButtonProcessingState() {
        btnRecord.text = "Обработка..."
        btnRecord.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#424242"))
    }

    private fun setButtonIdleState() {
        btnRecord.text = "Записать голос"
        btnRecord.backgroundTintList = null
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}