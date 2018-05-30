package com.example.aayushi.gcmexample;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by Aayushi on 8/10/2017.
 */
public class GSMRegistrationIntentService extends IntentService {

    public  static final String RegistrationSucess="RegistrationSuccess";
    public static final String RegistrationError="RegistrationError";




    public GSMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGcm();



    }

    private void registerGcm() {
        Intent RegistrationComplete=null;
        String token=null;

        try {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.w("GcmRegIntService","token"+token);
            RegistrationComplete=new Intent(RegistrationSucess);
            RegistrationComplete.putExtra("token",token);
            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(token, "/topics/my_little_topic", null);

        }
        catch (Exception e)
        {
            Log.w("GcmRegIntService",RegistrationError);
            RegistrationComplete=new Intent(RegistrationError);

        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(RegistrationComplete);



        }

}
