package com.auxilio.auxilio;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/*
    Created by: Dario Molina
    Date: 8/22/2018
 */

public class SMS
{

    private Context applicationContext;
    private BroadcastReceiver messageSendReceiver, messageDeliveredReceiver;
    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";

    public SMS(Context context)
    {
        applicationContext = context;
        messageSendReceiver = null;
        messageDeliveredReceiver = null;
    }

    public void registerReceivers()
    {
        messageSendReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(applicationContext, "SMS Sent", Toast.LENGTH_SHORT).show();
                        Log.d("RESULT OK", "SMS SENT!!!!!!!");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(applicationContext, "Generic Failure. Please Try Again.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(applicationContext, "No Service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(applicationContext, "Null PDU. Please Try Again.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(applicationContext, "Airplane Mode ON. Check Connection. SMS Not Send.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        messageDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(applicationContext, "SMS Delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(applicationContext, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        applicationContext.registerReceiver(messageSendReceiver, new IntentFilter(SENT));
        applicationContext.registerReceiver(messageDeliveredReceiver , new IntentFilter(DELIVERED));
    }

    public void sendSMS(String phoneNumber, String message)
    {
        PendingIntent sentPI = PendingIntent.getBroadcast(applicationContext, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(applicationContext, 0, new Intent(DELIVERED), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    public void unRegisterReceivers()
    {
        applicationContext.unregisterReceiver(messageSendReceiver);
        applicationContext.unregisterReceiver(messageDeliveredReceiver);
    }

}
