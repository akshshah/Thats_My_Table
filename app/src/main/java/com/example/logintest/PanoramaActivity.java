package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

public class PanoramaActivity extends AppCompatActivity {
    private static final String TAG = "PanoramaActivity";

    private GyroscopeObserver gyroscopeObserver;
    private ImageView image;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);

        image = findViewById(R.id.panorama_image);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getInt("restaurant");
            Log.d(TAG, "onCreate: " + id);
        }

        if(id%2 == 0){
            image.setImageDrawable(getResources().getDrawable(R.drawable.panorama));
        }else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.panorama2));
        }

        gyroscopeObserver = new GyroscopeObserver();

        gyroscopeObserver.setMaxRotateRadian(Math.PI/6);

        PanoramaImageView panoramaImageView = findViewById(R.id.panorama_image);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }
}
