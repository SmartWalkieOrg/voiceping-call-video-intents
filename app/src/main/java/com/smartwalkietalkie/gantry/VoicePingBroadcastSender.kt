package com.smartwalkietalkie.gantry

import android.content.Context
import android.content.Intent
import timber.log.Timber

class VoicePingBroadcastSender(private val context: Context) {

    fun initiateCall(userId: Int) {
        val intent = Intent(INITIATE_CALL).apply {
            putExtra(KEY_USER_ID, userId)
        }
        Timber.d("initiateCall: $intent")
        context.sendBroadcast(intent)
    }

    fun answerCall() {
        val intent = Intent(ANSWER_CALL)
        Timber.d("answerCall: $intent")
        context.sendBroadcast(intent)
    }

    fun endCall() {
        val intent = Intent(END_CALL)
        Timber.d("endCall: $intent")
        context.sendBroadcast(intent)
    }

    companion object {
        const val INITIATE_CALL = "com.media2359.voiceping.intent.action.INITIATE_CALL"
        const val ANSWER_CALL = "com.media2359.voiceping.intent.action.ANSWER_CALL"
        const val END_CALL = "com.media2359.voiceping.intent.action.END_CALL"
        const val KEY_USER_ID = "user_id"
    }
}