package com.smartwalkietalkie.gantry

class CallEvent(val type: String, val state: String) {

    object Type {
        const val OUTGOING = "outgoing"
        const val INCOMING = "incoming"
    }

    object State {
        const val CALL_INITIATED = "call_initiated"
        const val CALL_RECEIVED = "call_received"
        const val CALL_ANSWERED = "call_answered"
        const val CALL_CANCELLED = "call_cancelled"
        const val CALL_REJECTED = "call_rejected"
        const val CALL_ESTABLISHED = "call_established"
        const val CALL_ENDED = "call_ended"
    }
}