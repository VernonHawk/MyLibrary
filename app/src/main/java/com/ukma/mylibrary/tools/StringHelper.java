package com.ukma.mylibrary.tools;

import android.text.TextUtils;

public class StringHelper {
    public static String camelCaseToWords(final String str) {
        return TextUtils.join(
            " ", str.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")
        );
    }
}
