package com.koioc.billables;

import android.app.Application;
import android.os.SystemClock;

public class SplashDelay extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        SystemClock.sleep(3000);
    }

}
