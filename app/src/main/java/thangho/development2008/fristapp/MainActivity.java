package thangho.development2008.fristapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.content.Intent;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.os.Handler;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Thread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //private Handler mHandler = new Handler();
    private Handler mHandler;
    private Thread mThread;
    public static final int SEND_MESSAGE = 1001;
    public static final int MESSAGE_COUNTS = 1002;
    AirplaneModeReceiver airplaneModeReceiver = new AirplaneModeReceiver();
    private Button start, stop, wait;
    private ImageButton addButton;

    //private TextView counterStart, counterStop;
    private TextView tvTimer;
    //public static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAG = "MainActivity";
    public int counter1, counter2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHandler();

        start = (Button) findViewById(R.id.startButton);
        wait = (Button) findViewById(R.id.waitButton);
        stop = (Button) findViewById(R.id.stopButton);
        addButton = (ImageButton) findViewById(R.id.imageButton);
        tvTimer = (TextView) findViewById(R.id.tvtimer);
//        counterStart = (TextView) findViewById(R.id.cntStart) ;
//        counterStop = (TextView) findViewById(R.id.cntStop);

        start.setOnClickListener(this);
        wait.setOnClickListener(this);
        stop.setOnClickListener(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
       Thread.dumpStack();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SEND_MESSAGE:
                        //Log.e(TAG, "send message successfully");
                        tvTimer.setText(String.valueOf(msg.arg1));
                        break;
                    case MESSAGE_COUNTS:
                        //Log.e(TAG, "send message successfully");
                        Toast.makeText(MainActivity.this, "number counts is ten", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        Thread.dumpStack();
        Log.i(TAG, "sen");
    }

    public void openActivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeReceiver, filter);
    }

    @Override
    public void onClick(View view) {
        if(view == start) {
            counter1++;
            startService(new Intent(this, ServiceMedia.class));
//            counterStart.setText(Integer.toString(counter1));
        }

        else if (view == wait) {
            Thread mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int countDown = 10;
                    do {
                        countDown--;

                        //Log.e(TAG, "send message successfully");
                        Message msg = new Message();
                        msg.what = SEND_MESSAGE;
                        msg.arg1 = countDown;
                        mHandler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            //Log.d(TAG, "someOtherMethod()", e);
                            e.printStackTrace();
                        }
                    } while (countDown > 0);
                    mHandler.sendEmptyMessage(MESSAGE_COUNTS);
                }
            });
            mThread.start();
            Thread.dumpStack();
        }

        else if(view == stop){
            counter2++;
            stopService(new Intent(this, ServiceMedia.class));
    //        counterStop.setText(Integer.toString(counter2));
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(airplaneModeReceiver);
    }
}
