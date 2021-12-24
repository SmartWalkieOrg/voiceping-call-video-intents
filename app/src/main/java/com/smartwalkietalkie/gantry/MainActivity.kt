package com.smartwalkietalkie.gantry

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartwalkietalkie.gantry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), VoicePingBroadcastReceiver.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vpReceiver: VoicePingBroadcastReceiver
    private lateinit var vpSender: VoicePingBroadcastSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vpReceiver = VoicePingBroadcastReceiver(this)
        vpSender = VoicePingBroadcastSender(this)
        showAppropriateLayout(Layout.INIT_CALL)
        binding.buttonCall.setOnClickListener {
            val userId = binding.editUserId.text.toString()
            if (userId.isBlank()) {
                binding.editUserId.error = "Cannot be empty!"
                binding.editUserId.requestFocus()
                return@setOnClickListener
            }
            val intUserId = userId.toIntOrNull() ?: 0
            if (intUserId < 1) {
                binding.editUserId.error = "Invalid ID!"
                binding.editUserId.requestFocus()
                return@setOnClickListener
            }
            showToast("Calling: $intUserId")
            vpSender.initiateCall(intUserId)
        }
        binding.buttonCancel.setOnClickListener {
            vpSender.endCall()
        }
        binding.buttonAnswer.setOnClickListener {
            vpSender.answerCall()
        }
        binding.buttonReject.setOnClickListener {
            vpSender.endCall()
        }
        binding.buttonEnd.setOnClickListener {
            vpSender.endCall()
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

    override fun onCallEvent(callEvent: CallEvent) {
        runOnUiThread {
            val layout = when (callEvent.state) {
                CallEvent.State.CALL_INITIATED -> Layout.CALLING
                CallEvent.State.CALL_RECEIVED -> Layout.INCOMING_CALL
                CallEvent.State.CALL_ESTABLISHED -> Layout.CALL_ESTABLISHED
                else -> Layout.INIT_CALL
            }
            showAppropriateLayout(layout)
        }
    }

    enum class Layout {
        INIT_CALL,
        CALLING,
        INCOMING_CALL,
        CALL_ESTABLISHED
    }
}