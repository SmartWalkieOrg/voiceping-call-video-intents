package com.smartwalkietalkie.gantry

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import timber.log.Timber

class VoicePingBroadcastReceiver(private val listener: Listener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        when (intent.action) {
            ACTION_CONNECTION_EVENT -> {
                val isConnected = intent.getBooleanExtra(KEY_IS_CONNECTED, false)
                listener.onConnectionEvent(isConnected)
            }
            ACTION_CALL_EVENT -> {
                val callState = intent.getStringExtra(KEY_CALL_STATE) ?: ""
                if (callState.isBlank()) return
                val userId = intent.getIntExtra(KEY_USER_ID, 0)
                Timber.d("onReceive, callState: $callState, userId: $userId")
                listener.onCallEvent(CallEvent(callState, userId))
            }
        }
    }

    interface Listener {
        fun onConnectionEvent(isConnected: Boolean)
        fun onCallEvent(callEvent: CallEvent)
    }

    companion object {
        private const val ACTION_CONNECTION_EVENT =
            "com.media2359.voiceping.intent.action.CONNECTION_EVENT"
        private const val ACTION_CALL_EVENT = "com.media2359.voiceping.intent.action.CALL_EVENT"
        private const val KEY_IS_CONNECTED = "is_connected"
        private const val KEY_CALL_STATE = "call_state"
        private const val KEY_USER_ID = "user_id"

        fun generateIntentFilter(): IntentFilter {
            return IntentFilter().apply {
                addAction(ACTION_CONNECTION_EVENT)
                addAction(ACTION_CALL_EVENT)
            }
        }
    }
}