package com.example.attime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.attime.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.generateQRCV.setOnClickListener( e -> {
            startActivity(new Intent(DashboardActivity.this, GenerateQRActivity.class));
        });

        binding.scanQRCV.setOnClickListener( e -> {
            startActivity(new Intent(DashboardActivity.this, ScannedQRorBarcodeActivity.class));
        });
    }
}