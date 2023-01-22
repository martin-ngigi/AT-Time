package com.example.attime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.africastalking.AfricasTalking;
import com.africastalking.AirtimeService;
import com.africastalking.airtime.AirtimeResponse;
import com.example.attime.databinding.ActivitySendAirtimeBinding;

public class SendAirtimeActivity extends AppCompatActivity {

    //declare views
    ActivitySendAirtimeBinding  binding;
    String phone;
    String TAG = "SendAirtimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_send_airtime);
        binding = ActivitySendAirtimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init
        binding.buyAirtimeBtn.setOnClickListener(e -> {
            phone = binding.phoneET.getText().toString();
            buyAirtime();
        });
    }

    private void buyAirtime() {
        //show progress bar
        binding.progressBar.setVisibility(View.VISIBLE);

        //Solve "android.os.NetworkOnMainThreadException" error
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String userName = ""; //get them from https://account.africastalking.com/apps/sandbox/settings/key
        String api_Key = "";

        //initialize sdk
        AfricasTalking.initialize(userName, api_Key);

        //get the airtime service
        AirtimeService airtimeService = AfricasTalking.getService(AfricasTalking.SERVICE_AIRTIME);

        //set the phone number, currency code an amount
        String phoneNumber = phone;
        String currencyCode = "KES";
        float amount = 5;

        /**
         * Kenya Limits are:
         * Lower:
         *      Safaricom - KES 5
         *      Airtel and Telkom - KES 10
         * Upper - KES 10,000
         */

        //send airtime
        try {
            //hide progress bar
            binding.progressBar.setVisibility(View.GONE);

            AirtimeResponse response = airtimeService.send(phoneNumber, currencyCode, amount);
            Toast.makeText(this, "Airtime Sent Successfully. Response is "+response, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "buyAirtime: Airtime Sent Successfully. Response is "+response);
        }
        catch (Exception e){
            //hide progress bar
            binding.progressBar.setVisibility(View.GONE);

            Toast.makeText(this, "Error occurred while sending airtime. Hint: "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "buyAirtime: Error sending airtime. Hint: "+e.toString());
        }
    }
}