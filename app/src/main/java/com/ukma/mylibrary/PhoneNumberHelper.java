package com.ukma.mylibrary;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.widget.TextView;


/**
 Quality of life class for working with phone numbers.
 */
class PhoneNumberHelper {
    public static
    void addFormatting(final TextView view) {
        view.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    public static
    String normalize(final String number) {
        return PhoneNumberUtils.normalizeNumber(number);
    }

    public static
    String normalize(final Editable number) {
        return normalize(number.toString());
    }
}
