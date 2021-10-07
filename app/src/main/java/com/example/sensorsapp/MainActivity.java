package com.example.sensorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
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
    CameraManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        mediaPlayer = MediaPlayer.create(this, R.raw.y);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        bluetoothAdapter = (BluetoothAdapter.getDefaultAdapter());
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        cm = (CameraManager) getSystemService(CAMERA_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int x = (int) event.values[1];
        if(x>=1){
            imageView.setImageResource(R.drawable.on);
            mediaPlayer.start();
            bluetoothAdapter.enable();
            wifiManager.setWifiEnabled(true);
            flashOn();
            Toast.makeText(this, "Bluetooth and wifi on", Toast.LENGTH_SHORT).show();

        }
        else {
            imageView.setImageResource(R.drawable.off);
            mediaPlayer.pause();
            bluetoothAdapter.disable();
            wifiManager.setWifiEnabled(false);
            flashOff();
            Toast.makeText(this, "Bluetooth and wifi off", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void flashOn(){
        try {
            String id = cm.getCameraIdList()[0];
            cm.setTorchMode(id, true);
        }
        catch (CameraAccessException e){

        }
    }
    public void flashOff(){
        try {
            String id = cm.getCameraIdList()[0];
            cm.setTorchMode(id, false);
        }
        catch (CameraAccessException e){

        }
    }
}