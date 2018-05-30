package com.example.aayushi.gcmexample;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Aayushi on 8/10/2017.
 */
public class GcmTokenRefreshListenerService extends InstanceIDListenerService {
    public void onTokenRefresh()
    {
        Intent intent=new Intent(this,GSMRegistrationIntentService.class);
        startService(intent);
    }

}
