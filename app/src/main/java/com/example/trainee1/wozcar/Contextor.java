package com.example.trainee1.wozcar;

import android.content.Context;

/**
 * Created by ton on 6/16/2016 AD.
 */
public class Contextor {

    private static Contextor instance = null;

    private Context mContext;

    private Contextor() {
    }

    public static Contextor getInstance() {
        if (instance == null) {
            instance = new Contextor();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
