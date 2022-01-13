package com.smartwalkietalkie.gantry

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.smartwalkietalkie.gantry.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity(), VoicePingBroadcastReceiver.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vpReceiver: VoicePingBroadcastReceiver
    private lateinit var vpSender: VoicePingBroadcastSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vpReceiver = VoicePingBroadcastReceiver(this)
        vpSender = VoicePingBroadcastSender(this)
        showAppropriateLayout(Layout.IDLE_CALL, 0)
        updateConnectionStatus(false)
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
            showAppropriateLayout(Layout.IDLE_CALL, 0)
            vpSender.endCall()
        }
        binding.buttonAnswer.setOnClickListener {
            vpSender.answerCall()
        }
        binding.buttonReject.setOnClickListener {
            showAppropriateLayout(Layout.IDLE_CALL, 0)
            vpSender.endCall()
        }
        binding.buttonEnd.setOnClickListener {
            showAppropriateLayout(Layout.IDLE_CALL, 0)
            vpSender.endCall()
        }
        binding.textAppVersion.text = getAppVersion()
        registerReceiver(vpReceiver, VoicePingBroadcastReceiver.generateIntentFilter())
        vpSender.requestConnectionEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        unregisterReceiver(vpReceiver)
    }

    private fun showAppropriateLayout(layout: Layout, userId: Int) {
        Timber.d("showAppropriateLayout: ${layout.name}")
        if (layout == Layout.IDLE_CALL) {
            binding.layoutIdleCall.visibility = View.VISIBLE
            binding.layoutOnCall.visibility = View.GONE
        } else {
            binding.layoutIdleCall.visibility = View.GONE
            binding.layoutOnCall.visibility = View.VISIBLE
            binding.buttonCancel.visibility =
                if (layout == Layout.OUTGOING_CALL) View.VISIBLE else View.GONE
            binding.buttonAnswer.visibility =
                if (layout == Layout.INCOMING_CALL) View.VISIBLE else View.GONE
            binding.buttonReject.visibility =
                if (layout == Layout.INCOMING_CALL) View.VISIBLE else View.GONE
            binding.progressCircular.visibility =
                if (layout == Layout.ESTABLISHING_CALL) View.VISIBLE else View.GONE
            binding.buttonEnd.visibility =
                if (layout == Layout.CALL_ESTABLISHED) View.VISIBLE else View.GONE
            binding.textStatus.text = when (layout) {
                Layout.OUTGOING_CALL -> "Calling"
                Layout.INCOMING_CALL -> "Incoming call"
                Layout.ESTABLISHING_CALL -> "Establishing call..."
                Layout.CALL_ESTABLISHED -> "Call established"
                else -> ""
            }
            binding.textUserId.text = "ID: $userId"
        }
    }

    private fun updateConnectionStatus(isConnected: Boolean) {
        binding.textConnectionStatus.text = if (isConnected) "CONNECTED" else "DISCONNECTED"
        val colorResId = if (isConnected) R.color.green_500 else R.color.red_500
        binding.textConnectionStatus.setTextColor(ContextCompat.getColor(this, colorResId))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun getAppVersion(): String {
        return "${BuildConfig.VERSION_NAME}-${BuildConfig.VERSION_CODE}-${BuildConfig.GIT_HASH}-${BuildConfig.BUILD_TYPE}"
    }

    override fun onConnectionEvent(isConnected: Boolean) {
        runOnUiThread {
            updateConnectionStatus(isConnected)
        }
    }

    override fun onCallEvent(callEvent: CallEvent) {
        runOnUiThread {
            val layout = when (callEvent.state) {
                CallEvent.State.CALL_INITIATED -> Layout.OUTGOING_CALL
                CallEvent.State.CALL_RECEIVED -> Layout.INCOMING_CALL
                CallEvent.State.CALL_ANSWERED -> Layout.ESTABLISHING_CALL
                CallEvent.State.CALL_ESTABLISHED -> Layout.CALL_ESTABLISHED
                else -> Layout.IDLE_CALL
            }
            showAppropriateLayout(layout, callEvent.userId)
        }
    }

    enum class Layout {
        IDLE_CALL,
        OUTGOING_CALL,
        INCOMING_CALL,
        ESTABLISHING_CALL,
        CALL_ESTABLISHED
    }
}