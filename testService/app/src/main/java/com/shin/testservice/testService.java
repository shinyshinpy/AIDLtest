package com.shin.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class testService extends Service {
    public testService() {
    }

    private final ITestInterface.Stub binder = new ITestInterface.Stub() {
        @Override
        public void sampleAPI() throws RemoteException {
            Log.i("testService", "test B");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        Log.i("testService", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("testService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}