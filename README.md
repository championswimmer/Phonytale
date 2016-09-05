# Phonytale
A library the helps in simple telephony tasks in Android like making/intercepting Calls, SMS, MMS and USSD codes.

## Getting Started

#### NOTE: Permissions on Marshmallow and future

In the library's AndroidManifest.xml you'll see the following permissions.
```xml
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.READ_CALL_LOG" />
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
    <uses-permission android:name="android.permission.WRITE_CALL_LOG" />
```
If you want to use them, in Marshmallow (and future), you will also need
to require those permissions during the first run
of the app from the user, to make sure the features work.

### Calls

#### Making Calls

### SMS

#### Sending SMS
To simple send a message to a recipient, you can use the static method
```java
SmsSender.sendSms(to, msg);
```
The arguments `to` and `msg` both must be in form of Strings. If `msg` is greater than 140 characters, it won't work.

#### Receiving SMS

### USSD

#### Sending USSD Codes

#### Intercepting USSD Responses

## Documentation

You can checkout the full [javadocs for Phonytale](http://championswimmer.in/Phonytale/)