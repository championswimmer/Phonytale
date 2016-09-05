package in.championswimmer.phonytale.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import in.championswimmer.phonytale.Caller;
import in.championswimmer.phonytale.PhonytaleUri;

/**
 * Created by championswimmer on 11/08/16.
 */
public class OutgoingCallService extends Service {

    public static final String TAG = "OutgoingCall";

    TelephonyManager tm;
    static PhoneStateListener psm = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null) {
            return 0;
        }



        if (intent.getAction().equals(Caller.ACTION_PLACE_CALL)) {
            Log.d(TAG, "onStartCommand: ");

            tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (psm != null) {
                tm.listen(psm, PhoneStateListener.LISTEN_NONE);

            }
            psm = new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    switch (state) {
                        case TelephonyManager.CALL_STATE_IDLE:
                            Log.d(TAG, "onCallStateChanged: IDLE" + incomingNumber);
                            break;
                        case TelephonyManager.CALL_STATE_OFFHOOK:
                            Log.d(TAG, "onCallStateChanged: OFFHOOK" + incomingNumber);
                            break;
                        case TelephonyManager.CALL_STATE_RINGING:
                            Log.d(TAG, "onCallStateChanged: RINGING" + incomingNumber);
                            break;
                    }
                }
            };


            String toPhone = intent.getData().getQueryParameter(PhonytaleUri.QUERY_TO);
            Caller.makeCall(toPhone, this);
            tm.listen(psm, PhoneStateListener.LISTEN_CALL_STATE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tm.listen(psm, PhoneStateListener.LISTEN_NONE);
                    psm = null;
                }
            }, 60000);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
