package in.championswimmer.phonytale.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.championswimmer.phonytale.SmsSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendSms (String to, String msg) {
        SmsSender.sendSms(to, msg);
    }
}
