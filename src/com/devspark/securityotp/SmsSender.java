package com.devspark.securityotp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author e.shishkin
 * 
 */
public class SmsSender {
    private static final String TAG = SmsSender.class.getSimpleName();

    private static final String ACTION_SENT = "com.devspark.securityotp.SENT";
    private static final String ACTION_DELIVERED = "com.devspark.securityotp.DELIVERED";

    private Context mContext;

    public SmsSender(Context mContext) {
        this.mContext = mContext;
    }

    public void onStart() {
        Log.i(TAG, "onStart()");
        mContext.registerReceiver(sentReceiver, new IntentFilter(ACTION_SENT));
        mContext.registerReceiver(deliveredReceiver, new IntentFilter(ACTION_DELIVERED));
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
        mContext.unregisterReceiver(sentReceiver);
        mContext.unregisterReceiver(deliveredReceiver);
    }

    public void send(String number, String text) {
        Log.i(TAG, "SEND SMS");
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_DELIVERED), 0);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, text, sentPI, deliveredPI);
    }

    private BroadcastReceiver sentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show();
                    // sendErrorBroadcast(SmsManager.RESULT_ERROR_GENERIC_FAILURE);
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private BroadcastReceiver deliveredReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
