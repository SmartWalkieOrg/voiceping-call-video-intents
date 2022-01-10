package com.smartwalkietalkie.gantry

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import timber.log.Timber

class VoicePingBroadcastReceiver(private val listener: Listener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        if (intent.action != INTENT_ACTION) return
        val callState = intent.getStringExtra(KEY_CALL_STATE) ?: ""
        if (callState.isBlank()) return
        val userId = intent.getIntExtra(KEY_USER_ID, 0)
        Timber.d("onReceive, callState: $callState, userId: $userId")
        listener.onCallEvent(CallEvent(callState, userId))
    }

    interface Listener {
        fun onCallEvent(callEvent: CallEvent)
    }

    companion object {
        private const val INTENT_ACTION = "com.media2359.voiceping.intent.action.CALL_EVENT"
        private const val KEY_CALL_STATE = "call_state"
        private const val KEY_USER_ID = "user_id"

        fun generateIntentFilter(): IntentFilter {
            return IntentFilter(INTENT_ACTION)
        }
    }
}