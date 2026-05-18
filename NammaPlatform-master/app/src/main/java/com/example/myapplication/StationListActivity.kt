package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class StationListActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private val stations = arrayOf(
        "KSR Bengaluru",
        "Mysuru Junction",
        "Hubballi Junction",
        "Mangalore Central",
        "Belagavi",
        "Shivamogga"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_list)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        val listView = findViewById<ListView>(R.id.stationList)
        val btnListenStations = findViewById<LinearLayout>(R.id.btnListenStations)

        val stationCodes = arrayOf("SBC", "MYS", "UBL", "MAQ", "BGM", "SMET")

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val layout = LinearLayout(this@StationListActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(40, 40, 40, 40)
                    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    params.setMargins(20, 20, 20, 20)
                    layoutParams = params
                    background = GradientDrawable().apply {
                        setColor(Color.parseColor("#13294B"))
                        cornerRadius = 35f
                    }
                }

                val textLayout = LinearLayout(this@StationListActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                val stationName = TextView(this@StationListActivity).apply {
                    text = stations[position]
                    textSize = 20f
                    setTextColor(Color.WHITE)
                    setTypeface(null, Typeface.BOLD)
                }

                val stationCode = TextView(this@StationListActivity).apply {
                    text = "Code: ${stationCodes[position]}"
                    textSize = 14f
                    setTextColor(Color.LTGRAY)
                }

                textLayout.addView(stationName)
                textLayout.addView(stationCode)

                val circle = TextView(this@StationListActivity).apply {
                    text = "${position + 1}"
                    textSize = 20f
                    gravity = Gravity.CENTER
                    setTextColor(Color.parseColor("#F7C948"))
                    setTypeface(null, Typeface.BOLD)
                    background = GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(Color.parseColor("#081B33"))
                        setStroke(4, Color.parseColor("#F7C948"))
                    }
                    layoutParams = LinearLayout.LayoutParams(120, 120)
                }

                layout.addView(textLayout)
                layout.addView(circle)
                return layout
            }
        }

        listView.adapter = adapter
        listView.dividerHeight = 0
        listView.setBackgroundColor(Color.parseColor("#081B33"))

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TrainListActivity::class.java)
            intent.putExtra("station", stations[position])
            startActivity(intent)
        }

        // MAKE THE LISTEN BUTTON WORK
        btnListenStations.setOnClickListener {
            // This converts the list of stations into a Kannada announcement
            val announcement = "ಲಭ್ಯವಿರುವ ರೈಲ್ವೆ ನಿಲ್ದಾಣಗಳು: ಬೆಂಗಳೂರು, ಮೈಸೂರು, ಹುಬ್ಬಳ್ಳಿ, ಮಂಗಳೂರು, ಬೆಳಗಾವಿ ಮತ್ತು ಶಿವಮೊಗ್ಗ."
            tts.speak(announcement, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale("kn", "IN"))
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}