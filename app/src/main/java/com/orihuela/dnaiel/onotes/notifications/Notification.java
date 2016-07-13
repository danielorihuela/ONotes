package com.orihuela.dnaiel.onotes.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.orihuela.dnaiel.onotes.MainActivity;
import com.orihuela.dnaiel.onotes.R;

public class Notification {
    Context ctx;
    NotificationCompat.Builder mBuilder;
    NotificationManager noti;
    Intent notificationIntent;
    PendingIntent pendingIntent;

    public Notification(Context ctx) {
        this.ctx = ctx;
    }

    public void makeNotify(int id, String title, String note){
        mBuilder = new NotificationCompat.Builder(ctx);
        noti = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationIntent = new Intent(ctx, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(ctx, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setSmallIcon(R.drawable.ic_statusbar);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(note);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setOngoing(true);
        noti.notify(id, mBuilder.build());
    }

    public void deleteNotify(int idNotif){
        mBuilder = new NotificationCompat.Builder(ctx);
        noti = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        noti.cancel(idNotif);
    }

    public void deleteAll(){
        mBuilder = new NotificationCompat.Builder(ctx);
        noti = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        noti.cancelAll();
    }

}
