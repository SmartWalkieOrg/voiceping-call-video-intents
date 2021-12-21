package com.smartwalkietalkie.gantry

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusTextView = findViewById<TextView>(R.id.status)

        val filter = IntentFilter()
        filter.addAction("INCOMING_CALL")
        val updateUIReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val userId = intent.getIntExtra(EXTRAS_USER_ID, -1)
                statusTextView.text = "Incoming call from $userId"
            }
        }
        registerReceiver(updateUIReceiver, filter)
    }

    companion object {
        const val EXTRAS_USER_ID = "user_id"
    }
}