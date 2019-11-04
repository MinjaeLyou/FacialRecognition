package com.google.android.gms.samples.vision.face.facetracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Analysis extends AppCompatActivity {
    public static List<Float> result = FaceTrackerActivity.result;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.analysis);
        System.out.println("Oneeeeee");

        Button btn = (Button) findViewById(R.id.button3);
//
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FaceTrackerActivity.this.complete();
            }
        });
        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.}
    }

}
