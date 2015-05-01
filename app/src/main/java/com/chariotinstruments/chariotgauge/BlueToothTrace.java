package com.chariotinstruments.chariotgauge;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;        // meyere, this should not be needed in the end

public class BlueToothTrace extends Activity implements Runnable{

    ImageButton             btnOne;
    ImageButton             btnTwo;
    TextView                traceOut;
    int                     lineCount;
    boolean                 paused;
    Thread                  thread;
    BluetoothSerialService  mSerialService;
    private static Handler workerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.blue_tooth_trace);

        //Get the mSerialService object from the UI activity.
        Object obj = PassObject.getObject();
        //Assign it to global mSerialService variable in this activity.
        mSerialService = (BluetoothSerialService) obj;

        //Check if the serial service object is null - assign the handler.
        if(mSerialService != null){
            //Update the BluetoothSerialService instance's handler to this activities.
            mSerialService.setHandler(mHandler);
        }

        paused          = false;
        btnOne          = (ImageButton) findViewById(R.id.btnOne);
        btnTwo          = (ImageButton) findViewById(R.id.btnTwo);
        traceOut        = (TextView)    findViewById(R.id.traceOut);

        traceOut.setMovementMethod(new ScrollingMovementMethod());

        thread = new Thread(BlueToothTrace.this);
        thread.start();
        traceOut.append("on create\n");
    }

    //Handles the data being sent back from the BluetoothSerialService class.
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(!paused){
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage;
                try {
                    readMessage = new String(readBuf, 0, msg.arg1);
                } catch (NullPointerException e) {
                    readMessage = "0";
                }

                Message workerMsg = workerHandler.obtainMessage(1, readMessage);
                workerMsg.sendToTarget();
//                paused = true; // meyere, for just one iteration
            }
        }
    };

    @Override
    public void run(){
        Looper.prepare();
        workerHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                testTextOut((String)msg.obj);
            }
        };
        Looper.loop();
    }

    private void testTextOut(String sValue){
        traceOut.append(sValue);        // 4/30/15 this only works for one iteration
//        traceOut.append("hi there!\n");
// /       Toast.makeText(this, sValue, Toast.LENGTH_LONG).show();
    }

    //Button one handling.
    public void buttonOneClick(View v){
        //Reset the max value.
        paused = false;
        btnTwo.setBackgroundResource(Color.TRANSPARENT);
    }

    //Button two handling.
    public void buttonTwoClick(View v){
        if(!paused){
            paused = true;
            btnTwo.setBackgroundResource(R.drawable.btn_bg_pressed);
        }else{
            paused = false;
            btnTwo.setBackgroundResource(Color.TRANSPARENT);
        }
    }

    //Activity transfer handling
    public void goHome(View v){
        PassObject.setObject(mSerialService);
        onBackPressed();
        finish();
    }
}

