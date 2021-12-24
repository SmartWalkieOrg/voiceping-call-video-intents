package com.smartwalkietalkie.gantry

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import timber.log.Timber

class VoicePingBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        if (intent.action != INTENT_ACTION) return
        val callType = intent.getStringExtra(KEY_CALL_TYPE) ?: ""
        val callEvent = intent.getStringExtra(KEY_CALL_EVENT) ?: ""
        if (callType.isBlank() || callEvent.isBlank()) return
        Timber.d("onReceive, callType: $callType, callEvent: $callEvent")
    }

    object CallType {
        const val OUTGOING = "outgoing"
        const val INCOMING = "incoming"
    }

    object CallEvent {
        const val CALL_INITIATED = "call_initiated"
        const val CALL_RECEIVED = "call_received"
        const val CALL_ANSWERED = "call_answered"
        const val CALL_CANCELLED = "call_cancelled"
        const val CALL_REJECTED = "call_rejected"
        const val CALL_ESTABLISHED = "call_established"
        const val CALL_ENDED = "call_ended"
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