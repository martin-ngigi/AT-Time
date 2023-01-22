package com.example.attime;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import com.example.attime.databinding.ActivityGenerateQractivityBinding;

public class GenerateQRActivity extends AppCompatActivity {

    String TAG = "GenerateQRActivity";
    ActivityGenerateQractivityBinding binding;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_generate_qractivity);
        binding = ActivityGenerateQractivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initializing onclick listener for button.
        binding.idBtnGenerateQR.setOnClickListener(e -> {
            data = binding.dataET.getText().toString();
            if (data.isEmpty()){
                Toast.makeText(this, "Error: Can not generate empty qr code.", Toast.LENGTH_SHORT).show();
            }
            generateQR();

        });
    }

    private void generateQR() {
        // below line is for getting
        // the windowmanager service.
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3/4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.getBitmap();
            binding.idIVQrcode.setImageBitmap(bitmap);
        }
        catch (Exception e){
            Toast.makeText(this, "Error generating qr. Hint: +e.toString()", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "generateQR: Error generating qr. Hint: "+e.toString() );
        }
    }
}