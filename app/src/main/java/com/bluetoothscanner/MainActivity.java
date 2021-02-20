package com.bluetoothscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_on, btn_off;
    BluetoothAdapter bt_adapter;
    Intent bt_enabling_intent;
    int bt_request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_on = (Button) findViewById(R.id.bluetooth_on_id);
        btn_off = (Button) findViewById(R.id.bluetooth_off_id);
        bt_adapter = BluetoothAdapter.getDefaultAdapter();
        bt_enabling_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        bt_request_code = 1;

        switchBluetoothOn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == bt_request_code){
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Bluetooth is Enabled", Toast.LENGTH_LONG).show();
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Bluetooth is Failed to Enable", Toast.LENGTH_LONG).show();

            }
        }
    }

    private  void switchBluetoothOn () {
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if the device supports bluetooth
                if(bt_adapter == null){
                    Toast.makeText(getApplicationContext(), "This Device does not support Bluetooth", Toast.LENGTH_LONG).show();
                } else{

                    if(!bt_adapter.isEnabled()){
                        startActivityForResult(bt_enabling_intent, bt_request_code);
                    }
                }
            }
        });
    }
}