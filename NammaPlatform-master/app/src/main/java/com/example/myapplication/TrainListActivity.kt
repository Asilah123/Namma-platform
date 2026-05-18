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
import android.widget.AbsListView
import java.util.Locale

class TrainListActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var trains: Array<String> // Move this to class level so TTS can access it

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_list)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        val txtStation = findViewById<TextView>(R.id.txtStation)
        val trainList = findViewById<ListView>(R.id.trainList)
        val btnListenTrains = findViewById<LinearLayout>(R.id.btnListenTrains)

        val station = intent.getStringExtra("station")
        txtStation.text = station

        val trainNumbers: Array<String>
        val arrivalTimes: Array<String>
        val departureTimes: Array<String>
        val platforms: Array<String>
        val trainTypes: Array<String>

        // Setup Data (Keep your existing when block)
        when (station) {
            "KSR Bengaluru" -> {
                trains = arrayOf("Bengaluru Mail", "Mysuru Express", "Tumkur Passenger")
                trainNumbers = arrayOf("#12657", "#16523", "#56213")
                arrivalTimes = arrayOf("05:30", "06:00", "08:00")
                departureTimes = arrayOf("06:00", "06:15", "08:10")
                platforms = arrayOf("1", "5", "8")
                trainTypes = arrayOf("Superfast", "Express", "Passenger")
            }
            "Mysuru Junction" -> {
                trains = arrayOf("Chamundi Express", "Kaveri Express", "Malgudi Passenger")
                trainNumbers = arrayOf("#16215", "#16021", "#56270")
                arrivalTimes = arrayOf("07:00", "09:30", "11:00")
                departureTimes = arrayOf("07:20", "09:45", "11:15")
                platforms = arrayOf("2", "4", "1")
                trainTypes = arrayOf("Express", "Superfast", "Passenger")
            }
            // ... (Keep all your other station cases here) ...
            else -> {
                trains = arrayOf("Malnad Express", "Sharavathi Express", "Shivamogga Passenger")
                trainNumbers = arrayOf("#16227", "#17315", "#56910")
                arrivalTimes = arrayOf("07:30", "11:00", "15:15")
                departureTimes = arrayOf("07:50", "11:20", "15:30")
                platforms = arrayOf("3", "5", "1")
                trainTypes = arrayOf("Express", "Superfast", "Passenger")
            }
        }

        // List Adapter (Keep your existing custom adapter code)
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, trains) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val layout = LinearLayout(this@TrainListActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(35, 35, 35, 35)
                    layoutParams = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT)
                    background = GradientDrawable().apply {
                        setColor(Color.parseColor("#13294B"))
                        cornerRadius = 35f
                    }
                }

                val leftLayout = LinearLayout(this@TrainListActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                val trainNameText = TextView(this@TrainListActivity).apply {
                    text = trains[position]
                    setTextColor(Color.WHITE)
                    textSize = 20f
                    setTypeface(null, Typeface.BOLD)
                }

                // ... (Add your other TextViews: trainNo, time, type here) ...

                leftLayout.addView(trainNameText)
                // (Add other views to leftLayout)

                val circle = TextView(this@TrainListActivity).apply {
                    text = platforms[position]
                    textSize = 24f
                    setTextColor(Color.parseColor("#F7C948"))
                    gravity = Gravity.CENTER
                    setTypeface(null, Typeface.BOLD)
                    background = GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(Color.parseColor("#081B33"))
                        setStroke(5, Color.parseColor("#F7C948"))
                    }
                    layoutParams = LinearLayout.LayoutParams(120, 120)
                }

                layout.addView(leftLayout)
                layout.addView(circle)
                return layout
            }
        }

        trainList.adapter = adapter

        trainList.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("trainName", trains[position])
            intent.putExtra("platform", platforms[position])
            startActivity(intent)
        }

        // MAKE THE LISTEN BUTTON WORK
        btnListenTrains.setOnClickListener {
            val trainNames = trains.joinToString(", ")
            val announcement = "ಮುಂದಿನ ರೈಲುಗಳು: $trainNames. ಹೆಚ್ಚಿನ ವಿವರಗಳಿಗಾಗಿ ರೈಲಿನ ಮೇಲೆ ಕ್ಲಿಕ್ ಮಾಡಿ."
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