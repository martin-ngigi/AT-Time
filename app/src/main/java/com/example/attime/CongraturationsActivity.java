package com.example.attime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.africastalking.AfricasTalking;
import com.africastalking.AirtimeService;
import com.africastalking.airtime.AirtimeResponse;
import com.example.attime.databinding.ActivityCongraturationsBinding;

public class CongraturationsActivity extends AppCompatActivity {

    private static final String TAG = "CongraturationsActivity";
    ActivityCongraturationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_congraturations);

        binding = ActivityCongraturationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //show progress
        binding.loginProgress.loadingProgress.setVisibility(View.VISIBLE);
        binding.successTV.setVisibility(View.GONE);


        buyAirtime();

        binding.proceedToHomeBtn.setOnClickListener( e -> {
            startActivity(new Intent(CongraturationsActivity.this, DashboardActivity.class));
        });

    }

    private void buyAirtime() {

        //Solve "android.os.NetworkOnMainThreadException" error
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String userName = Constants.userName; //get them from https://account.africastalking.com/apps/sandbox/settings/key
        String api_Key = Constants.api_Key;

        //initialize sdk
        AfricasTalking.initialize(userName, api_Key);

        //get the airtime service
        AirtimeService airtimeService = AfricasTalking.getService(AfricasTalking.SERVICE_AIRTIME);

        //set the phone number, currency code an amount
        String phoneNumber = Constants.PHONE;
        String currencyCode = "KES";
        //float amount = 5;
        float amount = Constants.AMOUNT;

        /**
         * Kenya Limits are:
         * Lower:
         *      Safaricom - KES 5
         *      Airtel and Telkom - KES 10
         * Upper - KES 10,000
         */

        //send airtime
        try {


            AirtimeResponse response = airtimeService.send(phoneNumber, currencyCode, amount);


            int numberSent = response.numSent; //when successfull numSent should be more than 1 else less than 1
            if (numberSent>=1){
                //hide progress bar
                binding.loginProgress.loadingProgress.setVisibility(View.GONE);

                Toast.makeText(this, "Airtime Sent Successfully. Response is "+response, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "buyAirtime: Airtime Sent Successfully. Response is "+response);
                binding.lottieAnimCongratulations.setVisibility(View.VISIBLE);
                binding.successTV.setVisibility(View.VISIBLE);

                sendSMS();
            }
            else{
                //hide progress bar
                binding.loginProgress.loadingProgress.setVisibility(View.GONE);
                binding.errorTV.setText("Error occurred. Could not reach Africas Talking at the moment.");
                binding.errorTV.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Error occurred. Could not reach Africas Talking at the moment.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error occurred. Could not reach Africas Talking at the moment.");

            }

            Log.i(TAG, "buyAirtime: Airtime Request Sent Successfully.  Response is "+response);

        }
        catch (Exception e){
            //hide progress bar
            binding.loginProgress.loadingProgress.setVisibility(View.GONE);

            binding.errorTV.setText("Error occurred. Could not reach Africa Talking at the moment. \nHint: "+e.toString());
            binding.errorTV.setVisibility(View.VISIBLE);
            binding.successTV.setVisibility(View.GONE);
            Toast.makeText(this, "Error occurred. Could not reach Africas Talking at the moment. Hint: "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error occurred. Could not reach Africas Talking at the moment. Hint: "+e.toString());
        }
    }

    private void sendSMS() {
        //send sms
        String messageTxt = "Hello, Thank you for attending the event. We hope to see you next time.";
        MySms mySms =  new MySms();
        mySms.sendSms(messageTxt);
    }
}