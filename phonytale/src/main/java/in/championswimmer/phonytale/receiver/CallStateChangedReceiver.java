package in.championswimmer.phonytale.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by championswimmer on 12/08/16.
 */
public class CallStateChangedReceiver extends BroadcastReceiver {

    public static final String TAG = "CallState";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Log.d(TAG, "onReceive: " + Intent.ACTION_NEW_OUTGOING_CALL);
            Log.d(TAG, "onReceive: NUMBER = " + intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
        }

        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            Log.d(TAG, "onReceive: " + TelephonyManager.ACTION_PHONE_STATE_CHANGED);
            Log.d(TAG, "onReceive: STATE = " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));
        }
    }
}
