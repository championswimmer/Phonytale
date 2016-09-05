package in.championswimmer.phonytale.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by championswimmer on 24/08/16.
 */
public abstract class USSDReceiveService extends AccessibilityService {
    public static final String TAG = "USSDServ";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: ");
        if (event.getClassName().equals("android.app.AlertDialog")) {
            Log.d(TAG, "onAccessibilityEvent: " + event.getText().toString());
            onUSSDReceived(event.getText().toString());
        }
    }

    @Override
    public void onInterrupt() {

    }


    public abstract void onUSSDReceived (String message);
}
