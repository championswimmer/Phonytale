package in.championswimmer.phonytale.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import in.championswimmer.phonytale.PhonytaleUri;
import in.championswimmer.phonytale.SmsSender;

/**
 * Created by championswimmer on 10/8/16.
 */
public abstract class SmsSendService extends Service {

    public static final String TAG = "PhonyTale:SMS";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null) {
            return START_STICKY;
        }

        Log.d(TAG, "onStartCommand: intent = " + intent.toString());

        if (intent.getAction().equals(SmsSender.ACTION_SEND_SMS)) {
            if (intent.getStringExtra(SmsSender.EXTRA_RECEPIENT) == null) {
                Log.e(TAG, "onStartCommand: Does not have extra = " + SmsSender.EXTRA_RECEPIENT);
                return 0;
            }

            if (SmsSender.getSmsSendObserverClass() == null) {
                SmsSender.sendSms(
                        intent.getStringExtra(SmsSender.EXTRA_RECEPIENT),
                        intent.getStringExtra(SmsSender.EXTRA_SMS_CONTENT)
                );
            } else {
                int smsId = SmsSender.sendSms(
                        intent.getStringExtra(SmsSender.EXTRA_RECEPIENT),
                        intent.getStringExtra(SmsSender.EXTRA_SMS_CONTENT),
                        SmsSender.getSmsSendObserverClass(),
                        this
                );
                onSmsSending(
                        intent.getStringExtra(SmsSender.EXTRA_RECEPIENT),
                        intent.getStringExtra(SmsSender.EXTRA_SMS_CONTENT),
                        smsId,
                        Integer.valueOf(intent.getData().getQueryParameter(PhonytaleUri.QUERY_INTERVAL))
                );

            }
        } else {
            Log.e(TAG, "onStartCommand: Does not have action = " + SmsSender.ACTION_SEND_SMS);

        }

        if (intent.getAction().equals(SmsSender.ACTION_SMS_DELIVERED)) {
            Log.d(TAG, "onStartCommand: delivered" + intent.getStringExtra(SmsSender.EXTRA_RECEPIENT));
            onSmsDelivered(
                    intent.getStringExtra(SmsSender.EXTRA_RECEPIENT),
                    intent.getStringExtra(SmsSender.EXTRA_SMS_CONTENT),
                    intent.getIntExtra(SmsSender.EXTRA_MSG_CODE, 0)
            );
        }
        if (intent.getAction().equals(SmsSender.ACTION_SMS_SENT)) {
            Log.d(TAG, "onStartCommand: sent" + intent.getStringExtra(SmsSender.EXTRA_RECEPIENT));
            onSmsSent(
                    intent.getStringExtra(SmsSender.EXTRA_RECEPIENT),
                    intent.getStringExtra(SmsSender.EXTRA_SMS_CONTENT),
                    intent.getIntExtra(SmsSender.EXTRA_MSG_CODE, 0)
            );
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public abstract void onSmsSending(String recipient, String message, int smsId, int interval);
    public abstract void onSmsSent(String recipient, String message, int smsId);
    public abstract void onSmsDelivered(String recipient, String message, int smsId);
}
