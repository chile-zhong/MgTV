/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2016/7/22.
 */
package com.hunan.mgtv.api;


import android.content.Context;

/**
 * TODO
 *
 * @author Xiaodong

 */
public class ClientManager {

    public static final String LOG_TAG = "ClientManager";

    private static ClientManager sInstance;
    private static boolean sInitialed = false;
    private static Context mContext;

    private ChargeClient mChargeClient = null;

    private ClientManager(){
    }

    public static void init(Context context){
        mContext = context;
        if(sInitialed){
            return;
        }
        sInitialed = true;
    }

    public static ClientManager getInstance(){
        if (!sInitialed) {
            return null;
        }

        if (sInstance == null) {
            synchronized (ClientManager.class) {
                if (sInstance == null) {
                    sInstance = new ClientManager();

                }
            }
        }
        return sInstance;
    }


    //add by zhongzhi
    public ChargeClient getChargeClient() {
        if (mChargeClient == null) {
            synchronized (ClientManager.class) {
                if (mChargeClient == null) {
                    mChargeClient = new ChargeClientImpl(mContext);
                }
            }
        }

        return mChargeClient;
    }


    public void refreshClient() {
        mChargeClient = null;
    }
}
