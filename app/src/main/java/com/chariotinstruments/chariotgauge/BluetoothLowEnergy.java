package com.chariotinstruments.chariotgauge;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Mike on 4/5/15.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothLowEnergy {
    private static final boolean D = false;
    private static final int BYTE_LENGTH = 1024;
    BluetoothManager btManager;


    //Constructor
    public BluetoothLowEnergy(Context context, Handler handler){
        _btAdapter = BluetoothAdapter.getDefaultAdapter();
        _handler = handler;
        btManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        _context = context;
    }

    //Member fields
    private final BluetoothAdapter _btAdapter;
    private Handler _handler;
    private int _currentState;
    private Context _context;

    //Getters/Setters
    public void setHandler(Handler handler){
        _handler = handler;
    }

    public int getState(){
        return _currentState;
    }


    //member functions
    private void startScan(){
        _btAdapter.startLeScan(leScanCallback);
    }

    private void stopScan(){
        _btAdapter.stopLeScan(leScanCallback);
    }

    private void connectController(BluetoothDevice bluetoothDevice, Context context){
        BluetoothGatt bluetoothGatt = bluetoothDevice.connectGatt(context, false, btleGattCallback);
    }

    //BLE callbacks
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            // your implementation here
            Toast.makeText(_context, "Scanning..", Toast.LENGTH_SHORT).show();
        }
    };

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            // this will get called anytime you perform a read or write characteristic operation
            //processData(characteristic.getValue());

            for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                //find descriptor UUID that matches Client Characteristic Configuration (0x2902)
                // and then call setValue on that descriptor

                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                gatt.writeDescriptor(descriptor);
            }
        }

        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
            // this will get called when a device connects or disconnects
            //TODO: case statement to handle various state changes.
            gatt.discoverServices();
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
            // this will get called after the client initiates a            BluetoothGatt.discoverServices() call
            List<BluetoothGattService> services = gatt.getServices();
            for (BluetoothGattService service : services) {
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
            }
        }

        public void onCharacteristicsDiscovered(List<BluetoothGattCharacteristic> characteristic){

        }

        private void processData(byte[] data){
            String newData;
            newData = new String(data, 0, BYTE_LENGTH);
            Log.d("BLE", newData);
        }
    };




}