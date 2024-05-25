package com.qkrisi.logomote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qkrisi.logomote.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static {
        System.loadLibrary("logomote");
    }

    private ActivityMainBinding binding;
    private SensorManager sensorManager;

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    private final float[] orientationOffset = new float[3];

    private int LastMove = 0;
    private int LastRotate = 0;

    private final int OrientTreshold = 10;
    private final int OrientTreshold2 = OrientTreshold * -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button connectBtn = binding.connectButton;
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsConnected())
                {
                    Disconnect();
                    connectBtn.setText("Connect");
                }
                else
                {
                    orientationOffset[0] = orientationAngles[0];
                    orientationOffset[1] = orientationAngles[1];
                    orientationOffset[2] = orientationAngles[2];
                    String name = Connect(
                            binding.ipText.getText().toString(),
                            Integer.parseInt(binding.portText.getText().toString()),
                            binding.nameText.getText().toString());
                    boolean success = !Objects.equals(name, "");
                    if(success)
                    {
                        connectBtn.setText("Disconnect");
                        binding.nameText.setText(name);
                    }
                    else
                    {
                        Alert("Connection failed");
                    }
                }
            }
        });
        binding.penButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsConnected())
                    SendCommand("_toll");
            }
        });
    }

    public void Alert(String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void UpdateOrientation()
    {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        orientationAngles[0] *= 90f;
        orientationAngles[1] *= 90f;
        orientationAngles[2] *= 90f;
        orientationAngles[0] -= orientationOffset[0];
        orientationAngles[1] -= orientationOffset[1];
        orientationAngles[2] -= orientationOffset[2];
        binding.textRot.setText(String.format("%s %s %s", orientationAngles[0], orientationAngles[1], orientationAngles[2]));
        SendOrientation();
    }

    private void SendOrientation()
    {
        float pitch = orientationAngles[1];
        float roll = orientationAngles[2];
        int move = 0;
        int rotate = 0;
        if(pitch > OrientTreshold)
            move = 1;
        if(pitch < OrientTreshold2)
            move = -1;
        if(roll > OrientTreshold)
            rotate = 1;
        if(roll < OrientTreshold2)
            rotate = -1;
        if(move != LastMove || rotate != LastRotate)
        {
            LastMove = move;
            LastRotate = rotate;
            if(IsConnected())
                SendCommand(String.format("mozgat %d %d", move, rotate));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
            UpdateOrientation();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
            UpdateOrientation();
        }
    }

    public native String Connect(String host, int port, String name);
    public native boolean IsConnected();
    public native void Disconnect();
    public native void SendCommand(String command);
}