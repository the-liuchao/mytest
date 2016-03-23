package com.example.the_liuchao.myapplication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by the_liuchao on 2016/3/20.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(true);
    }
}
