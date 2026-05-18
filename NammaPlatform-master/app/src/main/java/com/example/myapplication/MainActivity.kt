package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var announcementText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        val txtHeaderTrainName = findViewById<TextView>(R.id.txtHeaderTrainName) // NEW
        val txtPlatform = findViewById<TextView>(R.id.txtPlatform)
        val txtArrival = findViewById<TextView>(R.id.txtArrival)
        val txtDeparture = findViewById<TextView>(R.id.txtDeparture)
        val txtTrain = findViewById<TextView>(R.id.txtTrain)
        val txtCoach = findViewById<TextView>(R.id.txtCoach)
        val coachContainer = findViewById<LinearLayout>(R.id.coachContainer)
        val btnStand = findViewById<Button>(R.id.btnStand)
        val btnSpeak = findViewById<Button>(R.id.btnSpeak)

        // Get Data from Intent
        val trainName = intent.getStringExtra("trainName") ?: "Train Details"
        val platform = intent.getStringExtra("platform") ?: "1"

        // Set Data to Views Dynamically
        txtHeaderTrainName.text = trainName // This fixes your Mysuru Express problem!
        txtTrain.text = trainName
        txtPlatform.text = platform
        txtArrival.text = "Arrival: 06:00"
        txtDeparture.text = "Departure: 06:15"

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Kannada Announcement logic
        announcementText = when (trainName) {
            "Bengaluru Mail" -> "ಬೆಂಗಳೂರು ಮೇಲ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ. ದಯವಿಟ್ಟು ಗಮನಿಸಿ"
            "Mysuru Express" -> "ಮೈಸೂರು ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ. ದಯವಿಟ್ಟು ಗಮನಿಸಿ"
            "Tumkur Passenger" -> "ತುಮಕೂರು ಪ್ಯಾಸೆಂಜರ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Chamundi Express" -> "ಚಾಮುಂಡಿ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Kaveri Express" -> "ಕಾವೇರಿ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Malgudi Passenger" -> "ಮಾಲ್ಗುಡಿ ಪ್ಯಾಸೆಂಜರ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Rani Chennamma Express" -> "ರಾಣಿ ಚೆನ್ನಮ್ಮ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Gol Gumbaz Express" -> "ಗೋಲ್ ಗುಂಬಜ್ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Mangalore Mail" -> "ಮಂಗಳೂರು ಮೇಲ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "West Coast Express" -> "ವೆಸ್ಟ್ ಕೋಸ್ಟ್ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Goa Express" -> "ಗೋವಾ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            "Malnad Express" -> "ಮಲ್ನಾಡ್ ಎಕ್ಸ್‌ಪ್ರೆಸ್ ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
            else -> "ರೈಲು ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${platform}ಕ್ಕೆ ಬರುತ್ತಿದೆ"
        }

        // Create Coach Layout
        val coaches = arrayOf("Engine", "GS", "S1", "S2", "S3", "A1", "A2", "B1", "General")
        for (coach in coaches) {
            val tv = TextView(this)
            tv.text = coach
            tv.textSize = 18f
            tv.setTextColor(Color.WHITE)
            tv.gravity = Gravity.CENTER
            tv.setPadding(40, 40, 40, 40)

            when {
                coach == "Engine" -> tv.setBackgroundColor(Color.DKGRAY)
                coach.startsWith("S") -> tv.setBackgroundColor(Color.parseColor("#1565C0"))
                coach.startsWith("A") || coach.startsWith("B") -> tv.setBackgroundColor(Color.parseColor("#8E24AA"))
                else -> tv.setBackgroundColor(Color.parseColor("#D32F2F"))
            }

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(20, 0, 20, 0)
            tv.layoutParams = params

            tv.setOnClickListener {
                txtCoach.text = "Selected Coach: $coach"
                val message = when (coach) {
                    "A1", "A2", "B1" -> "Stand near the rear side of platform"
                    "GS", "General" -> "Stand near the front side of platform"
                    else -> "Stand near the middle of platform"
                }
                btnStand.text = message
            }
            coachContainer.addView(tv)
        }

        btnStand.setOnClickListener {
            tts.speak("ದಯವಿಟ್ಟು ಸರಿಯಾದ ಕೋಚ್ ಬಳಿ ನಿಲ್ಲಿ", TextToSpeech.QUEUE_FLUSH, null, null)
        }

        btnSpeak.setOnClickListener {
            tts.speak(announcementText, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale("kn", "IN"))
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}