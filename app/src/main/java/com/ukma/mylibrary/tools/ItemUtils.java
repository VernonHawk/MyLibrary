package com.ukma.mylibrary.tools;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

public class ItemUtils {
    public enum BookState {
        FREE,
        RESERVED
    }

    public enum OrderType {
        ACTUAL,
        RESERVED
    }

    public static int ResourceColorToColor(final Context context, final int color) {
        return ResourcesCompat.getColor(context.getResources(), color, null);
    }
}
