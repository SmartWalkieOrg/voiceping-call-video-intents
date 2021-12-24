package com.smartwalkietalkie.gantry

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import timber.log.Timber

class VoicePingBroadcastReceiver(val listener: Listener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        if (intent.action != INTENT_ACTION) return
        val callType = intent.getStringExtra(KEY_CALL_TYPE) ?: ""
        val callState = intent.getStringExtra(KEY_CALL_EVENT) ?: ""
        if (callType.isBlank() || callState.isBlank()) return
        Timber.d("onReceive, callType: $callType, callState: $callState")
        listener.onCallEvent(CallEvent(callType, callState))
    }

    interface Listener {
        fun onCallEvent(callEvent: CallEvent)
    }

    companion object {
        private const val INTENT_ACTION = "com.media2359.voiceping.intent.action.CALL_EVENT"
        private const val KEY_CALL_TYPE = "call_type"
        private const val KEY_CALL_EVENT = "call_event"

        fun generateIntentFilter(): IntentFilter {
            return IntentFilter(INTENT_ACTION)
        }
    }
}