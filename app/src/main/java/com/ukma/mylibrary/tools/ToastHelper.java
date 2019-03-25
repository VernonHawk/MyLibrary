package com.ukma.mylibrary.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static void show(final Context context, final int msgId, int length) {
        Toast.makeText(context, msgId, length).show();
    }

    public static void show(final Context context, final String msg, int length) {
        Toast.makeText(context, msg, length).show();
    }

    public static void show(final Context context, final int msgId) {
        show(context, msgId, Toast.LENGTH_LONG);
    }

    public static void show(final Context context, final String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }
}
