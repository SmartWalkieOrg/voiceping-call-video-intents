# Guard App

An app that can interact
with [VoicePing](https://play.google.com/store/apps/details?id=com.media2359.voiceping.store) app
via broadcast. This app can get connection state of VoicePing app and also can do call
functionality.

## Call functionality

### Listen for call state change

To receive call state event from VoicePing, this app needs to listen for call event from VoicePing.

- Intent action: ```com.media2359.voiceping.intent.action.CALL_EVENT```
- Data to be received:
    - ```call_state```
    - ```user_id```
- Call States:
    - CALL_INITIATED = ```call_initiated```
    - CALL_RECEIVED = ```call_received```
    - CALL_ANSWERED = ```call_answered```
    - CALL_CANCELLED = ```call_cancelled```
    - CALL_REJECTED = ```call_rejected```
    - CALL_ESTABLISHED = ```call_established```
    - CALL_ENDED = ```call_ended```

### Initiate call

To initiate a call, this app needs to send an intent via broadcast

- Intent action: ```com.media2359.voiceping.intent.action.INITIATE_CALL```
- Data to be sent: ```user_id```

### Answer call

To answer an incoming call, this app needs to send an intent via broadcast

- Intent action: ```com.media2359.voiceping.intent.action.ANSWER_CALL```

### End call

To cancel an outgoing call, reject an incoming call, or end an established call, this app needs to send an intent via broadcast

- Intent action: ```com.media2359.voiceping.intent.action.END_CALL```

## Connection Event

### Listen for connection state change

To get the connection state of VoicePing, this app needs to listen for a connection-event broadcast.

- Intent action: ```com.media2359.voiceping.intent.action.CONNECTION_EVENT```.
- Data to be received: ```is_connected```

On the event, this app can know whether VoicePing is connected or not, by getting ```is_connected``` data from the event.

### Request connection event

The connection event is only be sent by VoicePing if there is a connection change. If this app needs
to get connection event of VoicePing without need to wait for connection event, then this app can
send request-connection-event
broadcast.

- Intent action: ```com.media2359.voiceping.intent.action.REQUEST_CONNECTION_EVENT```.
