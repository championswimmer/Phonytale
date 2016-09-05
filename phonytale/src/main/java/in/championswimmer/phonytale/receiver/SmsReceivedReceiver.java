package in.championswimmer.phonytale.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by championswimmer on 9/8/16.
 */
public abstract class SmsReceivedReceiver extends BroadcastReceiver {

    public static final String TAG = "SmsReceived";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Log.d(TAG, "onReceive: SMS Received");
            Bundle extras = intent.getExtras();


            if ( extras != null )
            {
                Object[] smsextras = (Object[]) extras.get( "pdus" );
                String format = extras.getString("format");
                String smsMsgBody = "", smsSender = "";

                for ( int i = 0; i < smsextras.length; i++ )
                {
                    SmsMessage smsmsg = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i], format);
                    } else {
                        smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);
                    }
                    smsMsgBody += smsmsg.getMessageBody();
                    smsSender = smsmsg.getOriginatingAddress();
                }
                Log.d(TAG, "onReceive: " + smsSender + " : " + smsMsgBody);
                onSmsReceived(context, smsSender, smsMsgBody);
            }
        }
    }
    public abstract void onSmsReceived(Context c, String sender, String msgBody);
}
