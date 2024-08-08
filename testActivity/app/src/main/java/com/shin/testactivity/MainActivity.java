package com.shin.testactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shin.testservice.ITestInterface;

public class MainActivity extends AppCompatActivity {

    private Button startServiceButton;
    private Button callAidlApiButton;
    private boolean isServiceConnecting;
    private ITestInterface testInterface;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("testActivity", "onServiceConnected");
            isServiceConnecting = true;

            testInterface = ITestInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("testActivity", "onServiceDisconnected");
            isServiceConnecting = false;

            testInterface = null;
        }
    };

    private final View.OnClickListener onClickStartService = view -> {
        Log.i("testActivity", "start service");
        Intent intent = new Intent();
        intent.setClassName("com.shin.testservice", "com.shin.testservice.testService");
        intent.setComponent(new ComponentName("com.shin.testservice", "com.shin.testservice.testService"));
        intent.setAction("com.shin.testservice.SERVICE_ACTION");

        Context context = getApplicationContext();
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    };

    private final View.OnClickListener onCallAidlApi = view -> {
        if(!isServiceConnecting) {
            Log.w("testActivity", "service is not connected");
            return;
        }
        if(testInterface == null) {
            Log.w("testActivity", "AIDL interface is null");
            return;
        }

        try {
            testInterface.sampleAPI();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
    }

    private void setView() {
        startServiceButton = findViewById(R.id.StartService);
        startServiceButton.setOnClickListener(onClickStartService);

        callAidlApiButton = findViewById(R.id.CallAidlApi);
        callAidlApiButton.setOnClickListener(onCallAidlApi);
    }
}