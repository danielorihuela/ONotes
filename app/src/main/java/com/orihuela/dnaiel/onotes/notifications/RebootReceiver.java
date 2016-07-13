package com.orihuela.dnaiel.onotes.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootReceiver extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, ServiceClass.class);
        context.startService(startServiceIntent);
    }
}
