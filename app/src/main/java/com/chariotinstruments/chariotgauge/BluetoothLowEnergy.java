package com.chariotinstruments.chariotgauge;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Mike on 4/5/15.
 */
public class BluetoothLowEnergy {
    private static final boolean D = false;


    //Constructor
    public BluetoothLowEnergy(Context context, Handler handler){
        _btAdapter = BluetoothAdapter.getDefaultAdapter();
        _handler = handler;
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


}
