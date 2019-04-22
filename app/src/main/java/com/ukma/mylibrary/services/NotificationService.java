package com.ukma.mylibrary.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ukma.mylibrary.R;
import com.ukma.mylibrary.ReaderActivity;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.managers.LibraryNotificationManager;

import org.json.JSONObject;

public class NotificationService extends FirebaseMessagingService {
    private static final String TAG = "NotificationService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            LibraryNotificationManager notificationManager = new LibraryNotificationManager(
                R.drawable.ic_bookmark_border_black_24dp,
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(),
                "books_notifications"
            );
            notificationManager.init(this, ReaderActivity.class);
            notificationManager.notify(this);
        }
    }

    @Override
    public void onNewToken(final String token) {
        super.onNewToken(token);
        final Context context = getApplicationContext();
        if (AuthManager.CURRENT_USER == null) {
            AuthManager.getManager(context)
                .setSignInListener(new AuthManager.SignInListener() {
                    @Override
                    public void onUserSignedIn() {
                        updateFCMToken(token, context);
                    }
                });
        } else {
            updateFCMToken(token, context);
        }
    }

    public static void updateFCMToken(String token, Context context) {
        try {
            API.call(Route.UpdateFCMToken)
                .body("fcm_token", token)
                .then(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject __) {}
                })
                .catchError(new APIResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                })
                .executeWithContext(context);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }
}
