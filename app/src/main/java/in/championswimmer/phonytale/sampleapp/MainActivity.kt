package `in`.championswimmer.phonytale.sampleapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import `in`.championswimmer.phonytale.SmsSender
import `in`.championswimmer.phonytale.sampleapp.examples.call.CallActivity
import `in`.championswimmer.phonytale.sampleapp.examples.sms.SMSActivity
import `in`.championswimmer.phonytale.sampleapp.examples.ussd.UssdActivity
import android.content.Intent

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSms -> startActivity(Intent(this, SMSActivity::class.java))
            R.id.btnCall -> startActivity(Intent(this, CallActivity::class.java))
            R.id.btnUssd -> startActivity(Intent(this, UssdActivity::class.java))
        }
    }
}
