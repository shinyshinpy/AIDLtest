package com.shin.testservice;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.shin.testservice", "com.shin.testservice.testService"));
        intent.setAction("com.shin.testservice.SERVICE_ACTION");

        Context context = getApplicationContext();
        context.startService(intent);

        Thread thread = new Thread(() -> {
            while(true)
            {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.i("testService", "running...");
            }
        });
        thread.start();
    }
}