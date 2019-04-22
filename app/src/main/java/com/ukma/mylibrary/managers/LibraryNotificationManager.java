package com.ukma.mylibrary.managers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class LibraryNotificationManager {
    private int icon;
    private String title;
    private String text;
    private String channelId;
    private NotificationCompat.Builder notificationBuilder;

    public LibraryNotificationManager(int icon, String title, String text, String channelId) {
        this.icon = icon;
        this.title = title;
        this.text = text;
        this.channelId = channelId;
    }

    public void init(Context context, Class c) {
        Intent intent = new Intent(context, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        this.notificationBuilder = new NotificationCompat.Builder(context, this.channelId)
                                        .setSmallIcon(this.icon)
                                        .setContentTitle(this.title)
                                        .setContentText(this.text)
                                        .setContentIntent(pendingIntent);

    }

    public void notify(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(this.channelId,
                    "Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, this.notificationBuilder.build());
    }
}
