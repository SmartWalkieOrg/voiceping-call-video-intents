package com.smartwalkietalkie.gantry

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartwalkietalkie.gantry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vpReceiver: VoicePingBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vpReceiver = VoicePingBroadcastReceiver()
        showAppropriateLayout(Layout.INIT_CALL)
        binding.buttonCall.setOnClickListener {
            val userId = binding.editUserId.text.toString()
            if (userId.isBlank()) {
                binding.editUserId.error = "Cannot be empty!"
                binding.editUserId.requestFocus()
                return@setOnClickListener
            }
            showToast("Calling: $userId")
            showAppropriateLayout(Layout.CALLING)
        }
        binding.buttonCancel.setOnClickListener { showAppropriateLayout(Layout.INCOMING_CALL) }
        binding.buttonAnswer.setOnClickListener {
            showAppropriateLayout(Layout.CALL_ESTABLISHED)
        }
        binding.buttonReject.setOnClickListener {
            showAppropriateLayout(Layout.INIT_CALL)
        }
        binding.buttonEnd.setOnClickListener {
            showAppropriateLayout(Layout.INIT_CALL)
        }
        registerReceiver(vpReceiver, VoicePingBroadcastReceiver.generateIntentFilter())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(vpReceiver)
    }

    private fun showAppropriateLayout(layout: Layout) {
        binding.layoutInitCall.visibility =
            if (layout == Layout.INIT_CALL) View.VISIBLE else View.GONE
        binding.layoutCalling.visibility = if (layout == Layout.CALLING) View.VISIBLE else View.GONE
        binding.layoutIncomingCall.visibility =
            if (layout == Layout.INCOMING_CALL) View.VISIBLE else View.GONE
        binding.layoutCallEstablished.visibility =
            if (layout == Layout.CALL_ESTABLISHED) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRAS_USER_ID = "user_id"
    }

    enum class Layout {
        INIT_CALL,
        CALLING,
        INCOMING_CALL,
        CALL_ESTABLISHED
    }
}