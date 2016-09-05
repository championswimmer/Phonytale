# Phonytale
A library the helps in simple telephony tasks in Android like making/intercepting Calls, SMS, MMS and USSD codes.

## Getting Started

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