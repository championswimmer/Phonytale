package in.championswimmer.phonytale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import in.championswimmer.phonytale.services.OutgoingCallService;

/**
 * Created by championswimmer on 11/08/16.
 */
public class Caller {

    public static final String TAG = "Caller";

    public static final String ACTION_PLACE_CALL = "airtel.comviva.mahindra.phonytale.ACTION_PLACE_CALL";

    public static void makeCall(String phonenumber, Context ctx) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_CALL);
        dialIntent.setData(Uri.parse("tel:"+phonenumber));
        dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(dialIntent);
    }

    public static void makeCallViaService(String phonenumber, Context ctx) {
        Intent oci = new Intent(ctx, OutgoingCallService.class);
        oci.setAction(Caller.ACTION_PLACE_CALL);
        oci.setData(PhonytaleUri.createOutgoingCall(
                phonenumber,
                0));

        ctx.startService(oci);

    }

    public static void createCallingTask(String phoneNumber, int interval, Context ctx) {
        Log.d(TAG, "createCallingTask: ");
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent oci = new Intent(ctx, OutgoingCallService.class);
        oci.setAction(ACTION_PLACE_CALL);
        oci.setData(PhonytaleUri.createOutgoingCall(phoneNumber, interval));

        Log.d(TAG, "createCallingTask: oci = " + oci.toString());

        PendingIntent pi = PendingIntent.getService(ctx, 222, oci, 0);

        Log.d(TAG, "createCallingTask: pi = " + pi.toString());

        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                (interval * 60 * 1000),
                pi
        );

    }

    public static void cancelCallingTask(String phoneNumber, int interval, Context ctx) {
        Log.d(TAG, "cancelCallingTask: ");
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent oci = new Intent(ctx, OutgoingCallService.class);
        oci.setAction(ACTION_PLACE_CALL);
        oci.setData(PhonytaleUri.createOutgoingCall(phoneNumber, interval));

        Log.d(TAG, "cancelCallingTask: oci = " + oci.toString());

        PendingIntent pi = PendingIntent.getService(ctx, 222, oci, 0);

        Log.d(TAG, "cancelCallingTask: pi = " + pi.toString());

        alarmManager.cancel(pi);

    }

}
