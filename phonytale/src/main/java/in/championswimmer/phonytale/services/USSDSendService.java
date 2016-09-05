package in.championswimmer.phonytale.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import in.championswimmer.phonytale.PhonytaleUri;
import in.championswimmer.phonytale.USSDSender;

public abstract class USSDSendService extends Service {

    public static final String TAG = "USSDSend";

    public USSDSendService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null) {
            return 0;
        }

        if (intent.getAction().equals(USSDSender.ACTION_SEND_USSD)) {
            Log.d(TAG, "onStartCommand: " + USSDSender.ACTION_SEND_USSD);

            String ussdCode = Uri.decode(intent.getData().getQueryParameter(PhonytaleUri.QUERY_TO));

            USSDSender.sendUSSD(
                    ussdCode,
                    this
            );
            onUSSDSend(ussdCode);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    public abstract void onUSSDSend(String ussdCode);
}
