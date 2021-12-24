# Guard App

An app that can initiate call functionality in VoicePing app via broadcast.

## Send Broadcast

Actions:

- INITIATE_CALL = ```com.media2359.voiceping.intent.action.INITIATE_CALL```
- ANSWER_CALL = ```com.media2359.voiceping.intent.action.ANSWER_CALL```
- END_CALL = ```com.media2359.voiceping.intent.action.END_CALL```

## Receive Broadcast

Action: ```com.media2359.voiceping.intent.action.CALL_EVENT```

Kinds:

```
private enum class Kind(val stringValue: String) {
    OUTGOING("outgoing"),
    INCOMING("incoming")
}
```

Events:

```
enum class Event(val stringValue: String) {
    CALL_INITIATED("call_initiated"),
    CALL_RECEIVED("call_received"),
    CALL_ANSWERED("call_answered"),
    CALL_CANCELLED("call_cancelled"),
    CALL_REJECTED("call_rejected"),
    CALL_ESTABLISHED("call_established"),
    CALL_ENDED("call_ended")
}
```
