package com.example.tugas4jihan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    double ax,ay,az;   // these are the acceleration in x,y and z axis
    String lastPosition = "";

    TextView txtSumbuX, txtSumbuY, txtSumbuZ, txtNavigasi, txtCurrNav;
    ImageView imgIlustrasi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fail! we dont have an accelerometer!
            Toast.makeText(this, "we dont have an accelerometer!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];

            txtSumbuX.setText("x: " + ax);
            txtSumbuY.setText("y: " + ay);
            txtSumbuZ.setText("z: " + az);

            changePosition();
        }
    }

    private void initializeViews() {
        txtSumbuX = (TextView) findViewById(R.id.txtSumbuX);
        txtSumbuY = (TextView) findViewById(R.id.txtSumbuY);
        txtSumbuZ = (TextView) findViewById(R.id.txtSumbuZ);
        txtNavigasi = (TextView) findViewById(R.id.txtNavigasi);
        txtCurrNav = (TextView) findViewById(R.id.txtCurrNav);

        imgIlustrasi = (ImageView) findViewById(R.id.imgIlustrasi);
    }

    private void changePosition() {
        String currPosition = "";
        if (ax < -7 && ax > -11) {
            currPosition = "Kanan";
        } else if (ax >= 8 && ax <= 11) {
            currPosition = "Kiri";
        } else if (ay >= 8 && ay < 11) {
            currPosition = "Atas";
        } else if (ay < -7 && ay > -11) {
            currPosition = "Bawah";
        }

        if (!lastPosition.equals(currPosition)) {
            lastPosition = currPosition;
            txtCurrNav.setText(lastPosition);

            String nav = txtNavigasi.getText().toString();
            nav = lastPosition + "\n" + nav;
            txtNavigasi.setText(nav);

            changeImagePosition();
        }
    }

    private void changeImagePosition() {
        Drawable res = getResources().getDrawable(R.drawable.arrowup);
        switch (lastPosition){
            case "Kanan": {
                res = getResources().getDrawable(R.drawable.arrowright);
            } break;
            case "Kiri": {
                res = getResources().getDrawable(R.drawable.arrowleft);
            } break;
            case "Atas": {
                res = getResources().getDrawable(R.drawable.arrowup);
            } break;
            case "Bawah": {
                res = getResources().getDrawable(R.drawable.arrowdown);
            }
        }

        imgIlustrasi.setImageDrawable(res);

    }
}
