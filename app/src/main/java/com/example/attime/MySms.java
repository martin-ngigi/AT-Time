package com.example.attime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.LinearLayout;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;

import java.io.IOException;
import java.util.List;

public class MySms {
    String TAG ="MySms";
    //send sms
    void sendSms(String message) {

        //SOLVE THE NETWORK ERROR/BUG BY :
        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        String username = Constants.userName;
        String apiKey = Constants.api_Key;//africastalking.com
        AfricasTalking.initialize(username, apiKey);
        // Initialize a service e.g. SMS
        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        // Use the service
        try {
            List<Recipient> response = sms.send(""+message, new String[]{""+Constants.PHONE}, true);
            Log.i(TAG, "sendSms: Sending SMS success.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
