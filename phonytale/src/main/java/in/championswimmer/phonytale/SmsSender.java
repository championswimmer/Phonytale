package in.championswimmer.phonytale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;

import in.championswimmer.phonytale.services.SmsSendService;

/**
 * Created by championswimmer on 8/8/16.
 */
public class SmsSender {

    public static final String TAG = "SmsSender";

    private static SmsManager sManager;
    private static Class<? extends Service> smsSendObserverClass = null;


    public static final String ACTION_SMS_SENT = "in.championswimmer.phonytale.SmsSender.ACTION_SMS_SENT";
    public static final String ACTION_SMS_DELIVERED = "in.championswimmer.phonytale.SmsSender.ACTION_SMS_DELIVERED";
    public static final String ACTION_SEND_SMS = "in.championswimmer.phonytale.SmsSender.ACTION_SEND_SMS";

    public static final String EXTRA_RECEPIENT = "recepient";
    public static final String EXTRA_MSG_CODE = "msg_code";
    public static final String EXTRA_SMS_CONTENT = "sms_content";

    public static void initialise () {
        if (sManager == null) {
            sManager = SmsManager.getDefault();
        }
    }


    /**
     * Method to send sms (without observing when it will get sent or delivered)
     *
     * @param destination Phone number of recepient
     * @param message Text to send
     */
    public static void  sendSms (String destination, String message) {
        initialise();

        sManager.sendTextMessage(
                destination,
                null,
                message,
                null,
                null
        );
    }

    /**
     * Method to send SMS if you want to observe for when the sms gets send and delivered
     *
     * @param destination Phone number of recepient
     * @param message Text to send
     * @param serviceClass The service to which an intent will be sent when message is sent and delivered
     * @param ctx Context using which the target service will be started
     */
    public static int sendSms (String destination, String message, Class<? extends Service> serviceClass, Context ctx) {
        initialise();

        int reqCode = (int) (System.currentTimeMillis() >> 8);

        Intent sentIntent = new Intent(ctx, serviceClass);
        sentIntent.setAction(ACTION_SMS_SENT);
        sentIntent.putExtra(EXTRA_RECEPIENT, destination);
        sentIntent.putExtra(EXTRA_SMS_CONTENT, message);
        sentIntent.putExtra(EXTRA_MSG_CODE, reqCode);
        Intent deliveredIntent = new Intent(ctx, serviceClass);
        deliveredIntent.setAction(ACTION_SMS_DELIVERED);
        deliveredIntent.putExtra(EXTRA_RECEPIENT, destination);
        deliveredIntent.putExtra(EXTRA_SMS_CONTENT, message);
        deliveredIntent.putExtra(EXTRA_MSG_CODE, reqCode);


        sManager.sendTextMessage(
                destination,
                null,
                message,
                PendingIntent.getService(ctx, reqCode, sentIntent, 0),
                PendingIntent.getService(ctx, reqCode, deliveredIntent, 0)
        );
        return reqCode;
    }

    public static void sendSmsViaService (String destination, String message, Context ctx) {
        Intent msi = new Intent(ctx, getSmsSendObserverClass());
        msi.setAction(SmsSender.ACTION_SEND_SMS);
        msi.setData(PhonytaleUri.createSendSMS(
                destination,
                message,
                3));
        msi.putExtra(SmsSender.EXTRA_RECEPIENT, destination);
        msi.putExtra(SmsSender.EXTRA_SMS_CONTENT, message);
        ctx.startService(msi);
    }

    public static void createSmsSendingTask(String recipient, String msgContent, int interval, Context ctx) {
        Log.d(TAG, "createSmsSendingTask: ");
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        //TODO: Make unique using data instead of extra
        Intent msi = new Intent(ctx, getSmsSendObserverClass());
        msi.setAction(SmsSender.ACTION_SEND_SMS);
        msi.setData(PhonytaleUri.createSendSMS(recipient, msgContent, interval));
        msi.putExtra(SmsSender.EXTRA_RECEPIENT, recipient);
        msi.putExtra(SmsSender.EXTRA_SMS_CONTENT, msgContent);


        Log.d(TAG, "createSmsSendingTask: intent = " + msi.toString());

        PendingIntent pi = PendingIntent.getService(
                ctx,
                111,
                msi,
                0
        );

        Log.d(TAG, "createSmsSendingTask: pendingintent = " + pi.toString());

        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                (interval * 60 * 1000),
                pi
        );

    }

    public static void cancelSmsSendingTask(String recipient, String msgContent, int interval, Context ctx) {
        Log.d(TAG, "cancelSmsSendingTask: ");
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent msi = new Intent(ctx, getSmsSendObserverClass());
        msi.setData(PhonytaleUri.createSendSMS(recipient, msgContent, interval));
        msi.setAction(SmsSender.ACTION_SEND_SMS);
        msi.putExtra(SmsSender.EXTRA_RECEPIENT, recipient);
        msi.putExtra(SmsSender.EXTRA_SMS_CONTENT, msgContent);


        Log.d(TAG, "cancelSmsSendingTask: intent = " + msi.toString());

        PendingIntent pi = PendingIntent.getService(
                ctx,
                111,
                msi,
                0
        );

        Log.d(TAG, "cancelSmsSendingTask: pendingintent = " + pi.toString());
        alarmManager.cancel(pi);
    }


    public static Class<? extends Service> getSmsSendObserverClass() {
        Log.d(TAG, "getSmsSendObserverClass: " + smsSendObserverClass.toString());
        if (smsSendObserverClass == null) {
            return SmsSendService.class;
        }
        return smsSendObserverClass;
    }

    public static void setSmsSendObserverClass(Class<? extends Service> smsSendObserverClass) {
        SmsSender.smsSendObserverClass = smsSendObserverClass;
        Log.d(TAG, "getSmsSendObserverClass: " + smsSendObserverClass.toString());

    }
}
