package in.championswimmer.phonytale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import in.championswimmer.phonytale.services.USSDSendService;

/**
 * Created by championswimmer on 12/08/16.
 */
public class USSDSender {

    public static final String TAG = "USSDSender";

    private static Class<? extends USSDSendService> ussdSendServiceClass;

    public static final String ACTION_SEND_USSD = "airtel.comviva.mahindra.phonytale.ACTION_SEND_USSD";

    public static void sendUSSD (String ussdCode, Context ctx) {

        Log.d(TAG, "sendUSSD: ");

        Uri ussdUri = Uri.parse("tel:" + Uri.encode(ussdCode));
        Intent ussdIntent = new Intent();
        ussdIntent.setAction(Intent.ACTION_CALL);
        ussdIntent.setData(ussdUri);
        ussdIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(ussdIntent);
    }

    public static void sendUSSDViaService(String ussdCode, Context ctx) {
        Intent usi = new Intent(ctx, getUssdSendServiceClass());
        usi.setAction(ACTION_SEND_USSD);
        usi.setData(PhonytaleUri.createSendUSSD(ussdCode, 0));

        ctx.startService(usi);
    }

    public static void createUSSDSendTask(String ussdCode, int interval, Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent usi = new Intent(ctx, getUssdSendServiceClass());
        usi.setAction(ACTION_SEND_USSD);
        usi.setData(PhonytaleUri.createSendUSSD(ussdCode, interval));

        Log.d(TAG, "createUSSDSendTask: usi = " + usi.toString());


        PendingIntent pi = PendingIntent.getService(ctx, 333, usi, 0);

        Log.d(TAG, "createUSSDSendTask: pi = " + pi.toString());

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                (interval * 60 * 1000),
                pi);

    }

    public static void cancelUSSDSendTask(String ussdCode, int interval, Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent usi = new Intent(ctx, USSDSendService.class);
        usi.setAction(ACTION_SEND_USSD);
        usi.setData(PhonytaleUri.createSendUSSD(ussdCode, interval));


        PendingIntent pi = PendingIntent.getService(ctx, 333, usi, 0);

        alarmManager.cancel(pi);

    }

    public static Class<? extends USSDSendService> getUssdSendServiceClass() {
        if (ussdSendServiceClass == null) {
            return USSDSendService.class;
        }
        return ussdSendServiceClass;
    }

    public static void setUssdSendServiceClass(Class<? extends USSDSendService> ussdSendServiceClass) {
        USSDSender.ussdSendServiceClass = ussdSendServiceClass;
    }
}
