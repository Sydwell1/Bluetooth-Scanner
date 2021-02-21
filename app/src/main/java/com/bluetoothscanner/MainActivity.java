package com.bluetoothscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //Variable declearing
    Button btn_on, btn_off, btn_scan;
    BluetoothAdapter bt_adapter;
    Intent bt_enabling_intent;
    int bt_request_code;
    ListView deviceDisplay;
    TextView header_text;
    ArrayList<String> str_Array = new ArrayList<String>();
    ArrayAdapter<String> ary_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variable initialization
        btn_on = (Button) findViewById(R.id.bluetooth_on_id);
        btn_off = (Button) findViewById(R.id.bluetooth_off_id);
        btn_scan = (Button) findViewById(R.id.scan_id);

        btn_scan.setVisibility(View.INVISIBLE);

        bt_adapter = BluetoothAdapter.getDefaultAdapter();
        bt_enabling_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        bt_request_code = 1;

        deviceDisplay = (ListView) findViewById(R.id.device_list);
        header_text = (TextView) findViewById(R.id.text_view);

        //Function calling
        bluetoothEnabler();
        bluetoothDisabler();
        pairedDeviceScanner();
        availableDevice();
    }


    //scanning for available bluetooth connectivity
    private void availableDevice() {
        btn_scan.setVisibility(View.VISIBLE);
        str_Array.clear(); // clearing the available device list
        bt_adapter.startDiscovery();

        IntentFilter intent_filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bt_receiver, intent_filter);
        ary_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, str_Array);
        deviceDisplay.setAdapter(ary_adapter);
    }

    BroadcastReceiver bt_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //check if a bluetooth device has been found
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                str_Array.add(device.getName());
                ary_adapter.notifyDataSetChanged();

            }
        }
    };

    //canceling bluetooth device discovery
    private void cancelDeviceScan() {
        unregisterReceiver(bt_receiver);
        bt_adapter.cancelDiscovery();
    }

    //getting all bluetooth paired to the device
    private void pairedDeviceScanner() {
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDeviceScan(); //cancelling device discovery

                Set<BluetoothDevice> bt = bt_adapter.getBondedDevices(); //
                String[] strings = new String[bt.size()];
                int index = 0;

                header_text.setText("Paired Devices");
                Toast.makeText(getApplicationContext(), "Scanning Paired Devices...", Toast.LENGTH_LONG).show();

                //checking if there are any paired devices
                if(bt.size() > 0){
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

    //disabling bluetooth
    private void bluetoothDisabler() {
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bt_adapter.isEnabled()){
                    bt_adapter.disable(); // disabling bluetooth
                    cancelDeviceScan(); // cancelling device discovery
                    header_text.setText(null); //clearing the header text
                    deviceDisplay.setAdapter(null); // clearing the display list
                    btn_scan.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Bluetooth is Disabled", Toast.LENGTH_LONG).show(); //message to confirm bluetooth has been disabled
                }

            }
        });
    }

    //Enabling bluetooth connectivity
    private  void bluetoothEnabler () {
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if the device supports bluetooth
                if(bt_adapter == null){
                    Toast.makeText(getApplicationContext(), "This Device does not support Bluetooth", Toast.LENGTH_LONG).show();
                } else{

                    if(!bt_adapter.isEnabled()){
                        btn_scan.setVisibility(View.VISIBLE);

                        header_text.setText("Available Devices");
                        availableDevice();

                        startActivityForResult(bt_enabling_intent, bt_request_code);
                    }else {
                        Toast.makeText(getApplicationContext(), "Bluetooth is Enabled", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //starting bluetooth connectivity
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

}

