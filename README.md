# Guard App

An app that can interact
with [VoicePing](https://play.google.com/store/apps/details?id=com.media2359.voiceping.store) app
via broadcast. This app can get connection state of VoicePing app and also can do call
functionality.

## Connection state

To get the connection state of VoicePing, this app needs to listen for a connection-event broadcast.
```com.media2359.voiceping.intent.action.CONNECTION_EVENT```. On the event, this app can know
whether VoicePing is connected or not, by getting ```is_connected``` data from the event.

The connection event is only be sent by VoicePing if there is a connection change. If this app needs
to get connection event of VoicePing without need to wait for connection event, then this app can
send request-connection-event
broadcast. ```com.media2359.voiceping.intent.action.REQUEST_CONNECTION_EVENT```.

## Call functionality

### Send broadcast

This app can initiate call, answer incoming call, and reject incoming call by sending broadcast.

Actions:

- INITIATE_CALL = ```com.media2359.voiceping.intent.action.INITIATE_CALL```
- ANSWER_CALL = ```com.media2359.voiceping.intent.action.ANSWER_CALL```
- END_CALL = ```com.media2359.voiceping.intent.action.END_CALL```

### Receive Broadcast

This app can also receive call state of VoicePing by listening for call event from VoicePing.

Action: ```com.media2359.voiceping.intent.action.CALL_EVENT```

Call States:

- CALL_INITIATED = ```call_initiated```
- CALL_RECEIVED = ```call_received```
- CALL_ANSWERED = ```call_answered```
- CALL_CANCELLED = ```call_cancelled```
- CALL_REJECTED = ```call_rejected```
- CALL_ESTABLISHED = ```call_established```
- CALL_ENDED = ```call_ended```
