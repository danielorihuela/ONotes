package com.orihuela.dnaiel.onotes.notifications;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.orihuela.dnaiel.onotes.MainActivity;
import com.orihuela.dnaiel.onotes.R;

public class ServiceClass extends Service {

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private void showNotification() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_statusbar)
                .setContentTitle(getText(R.string.restore_notif))
                .setContentText(getText(R.string.restore_notif_text));
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 200, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        startForeground(200, mBuilder.build());
    }
}
