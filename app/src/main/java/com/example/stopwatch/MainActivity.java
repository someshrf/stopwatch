package com.example.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button pauseButton;

    private TextView timerValue;

    Intent intent;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);

    }

    public void onStop(View V) {
        MainActivity.super.onStop();
        intent = new Intent(MainActivity.this, CounterService.class);
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(CounterService.BROADCAST_ACTION));
    }

    public void onClick(View V) {
        unregisterReceiver(broadcastReceiver);
        stopService(intent);
    }

    public void onReset(View V) {

    }

    private BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {

        int time = intent.getIntExtra("time",0);

        Log.d("Hello", "Time" + time);

        int mins = time/60;
        int secs = time%60;

        timerValue.setText(""+ mins+ ":"+ String.format("%02d",secs));
    }
}
