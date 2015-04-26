package com.chariotinstruments.chariotgauge;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class BlueToothTrace extends Activity{

    ImageButton             btnOne;
    ImageButton             btnTwo;
    TextView                traceOut;
    int                     lineCount;
    boolean                 paused;
    BluetoothSerialService  mSerialService;

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

        lineCount       = 0;
        paused          = false;
        btnOne          = (ImageButton) findViewById(R.id.btnOne);
        btnTwo          = (ImageButton) findViewById(R.id.btnTwo);
        traceOut        = (TextView)    findViewById(R.id.traceOut);

        traceOut.setMovementMethod(new ScrollingMovementMethod());

        testTextOut();
    }

    private void testTextOut(){
        String s = "";

        for(int x = 0; x < 100; x++){
            s = "Line:" + String.valueOf(x) + "\n";
            traceOut.append(s);
        }
//
//        traceOut.setText(s);

    }

    //Button one handling.
    public void buttonOneClick(View v){
        //Reset the max value.
        paused = false;
        btnTwo.setBackgroundResource(Color.TRANSPARENT);

//        traceOut.append("hi there\nnew line\n and another new line");
        for(int ii=0; ii<10; ii++) {
            traceOut.append("hi there ");
            traceOut.append(Integer.toString(lineCount));
            traceOut.append("\n");
            lineCount += 1;
        }
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

