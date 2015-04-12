package com.chariotinstruments.chariotgauge;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

/**
 * Created by Mike on 4/5/15.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothLowEnergy {
    private static final boolean D = false;
    BluetoothManager btManager;


    //Constructor
    public BluetoothLowEnergy(Context context, Handler handler){
        _btAdapter = BluetoothAdapter.getDefaultAdapter();
        _handler = handler;
        btManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    //Member fields
    private final BluetoothAdapter _btAdapter;
    private Handler _handler;
    private int _currentState;

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
        }
    };

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            // this will get called anytime you perform a read or write characteristic operation
        }

        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
            // this will get called when a device connects or disconnects
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
            // this will get called after the client initiates a            BluetoothGatt.discoverServices() call
        }
    };




}
