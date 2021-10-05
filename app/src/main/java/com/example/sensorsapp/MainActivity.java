package com.example.sensorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    ImageView imageView;
    SensorManager sensorManager;
    Sensor sensor;
    BluetoothAdapter bluetoothAdapter;
    WifiManager wifiManager;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        mediaPlayer = MediaPlayer.create(this, R.raw.y);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        bluetoothAdapter = (BluetoothAdapter.getDefaultAdapter());
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int x = (int) event.values[0];
        if(x!=0){
            imageView.setImageResource(R.drawable.on);
            mediaPlayer.start();
            bluetoothAdapter.enable();
            wifiManager.setWifiEnabled(true);
            Toast.makeText(this, "Bluetooth and wifi on", Toast.LENGTH_SHORT).show();
        }
        else {
            imageView.setImageResource(R.drawable.off);
            mediaPlayer.pause();
            bluetoothAdapter.disable();
            wifiManager.setWifiEnabled(false);
            Toast.makeText(this, "Bluetooth and wifi off", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}