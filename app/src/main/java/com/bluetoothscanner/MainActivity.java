package com.bluetoothscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button btn_on, btn_off, btn_scan;
    BluetoothAdapter bt_adapter;
    Intent bt_enabling_intent;
    int bt_request_code;
    ListView deviceDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_on = (Button) findViewById(R.id.bluetooth_on_id);
        btn_off = (Button) findViewById(R.id.bluetooth_off_id);
        btn_scan = (Button) findViewById(R.id.scan_id);

        bt_adapter = BluetoothAdapter.getDefaultAdapter();
        bt_enabling_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        bt_request_code = 1;
        deviceDisplay = (ListView) findViewById(R.id.device_list);

        bluetoothEnabler();
        bluetoothDisabler();
        bluetoothDeviceScanner();
    }

    private void bluetoothDeviceScanner() {
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> bt = bt_adapter.getBondedDevices();
                String[] strings = new String[bt.size()];
                int index = 0;

                if(bt.size() > 0){
                    Toast.makeText(getApplicationContext(), "Scanning Paired Devices...", Toast.LENGTH_LONG).show();

                    for(BluetoothDevice device: bt){
                        strings[index] = device.getName();
                        index++;
                    }
                    //Displaying all paired devices
                    ArrayAdapter<String> ary_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    deviceDisplay.setAdapter(ary_adapter);

                }

            }
        });
    }

    private void bluetoothDisabler() {
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bt_adapter.isEnabled()){
                    bt_adapter.disable(); // disabling bluetooth
                    deviceDisplay.setAdapter(null); // clearing the display list
                    Toast.makeText(getApplicationContext(), "Bluetooth is Disabled", Toast.LENGTH_LONG).show(); //message to confirm bluetooth has been disabled
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == bt_request_code) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth is Enabled", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth is Failed to Enable", Toast.LENGTH_LONG).show();

            }
        }
    }

    private  void bluetoothEnabler () {
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if the device supports bluetooth
                if(bt_adapter == null){
                    Toast.makeText(getApplicationContext(), "This Device does not support Bluetooth", Toast.LENGTH_LONG).show();
                } else{

                    if(!bt_adapter.isEnabled()){
                        startActivityForResult(bt_enabling_intent, bt_request_code);
                    }else {
                        Toast.makeText(getApplicationContext(), "Bluetooth is Enabled", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}