package in.championswimmer.phonytale;

import android.net.Uri;

/**
 * Created by championswimmer on 11/08/16.
 */
public class PhonytaleUri {

    public static final String SCHEME = "phonytale";
    public static final String HOST_SMS = "sms";
    public static final String HOST_CALL = "call";
    public static final String HOST_USSD = "ussd";
    public static final String PATH_SEND = "/send";
    public static final String PATH_OUTGOING = "/outgoing";
    public static final String QUES = "?";
    public static final String AND = "&";

    public static final String QUERY_TO = "to";
    public static final String QUERY_MSG = "msg";
    public static final String QUERY_USSD = "ussd";
    public static final String QUERY_INTERVAL = "interval";

    public static Uri createSendSMS(String recipient, String message, int interval) {
        return  Uri.parse(SCHEME + "://" + HOST_SMS + PATH_SEND + QUES
            + QUERY_TO + "=" + recipient + AND
            + QUERY_MSG + "=" + message + AND
            + QUERY_INTERVAL + "=" + interval);
    }

    public static Uri createOutgoingCall(String phoneNumber, int interval) {
        return  Uri.parse(SCHEME + "://" + HOST_SMS + PATH_OUTGOING + QUES
                + QUERY_TO + "=" + phoneNumber + AND
                + QUERY_INTERVAL + "=" + interval);
    }

    public static Uri createSendUSSD(String ussdCode, int interval) {
        return Uri.parse(SCHEME + "://" + HOST_USSD + PATH_SEND + QUES
                + QUERY_TO + "=" + Uri.encode(ussdCode) + AND
                + QUERY_INTERVAL + "=" + interval);
    }
}
