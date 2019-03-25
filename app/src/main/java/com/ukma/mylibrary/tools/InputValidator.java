package com.ukma.mylibrary.tools;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Pair;

import com.ukma.mylibrary.R;

import java.util.Map;

public class InputValidator {

    private static class Range<T extends Comparable<T>> {
        final T min;
        final T max;

        Range(final T min, final T max) {
            this.min = min;
            this.max = max;
        }

        int compareTo(final T val) {
            if (min.compareTo(val) > 0)
                return 1;

            if (max.compareTo(val) < 0)
                return -1;

            return 0;
        }
    }

    public interface Input {}

    public interface InvalidInputListener {
        void processError(final Input input, final int errStringId);
    }

    private static final Range PhoneNumberRange = new Range<>(8, 20);

    public static boolean areInputsValid(
        @NonNull final Map<Input, TextInputLayout> inputToLayout,
        final Map<Input, String> textInputs,
        final Map<Input, String> phoneNumberInputs,
        final Map<Input, String> passwordInputs,
        final Map<Pair<Input, Input>, String> passwordConfirmationInputs,
        final InvalidInputListener errorListener
    ) {
        // clear errors
        for (final TextInputLayout layout : inputToLayout.values())
            layout.setError(null);

        boolean isValid = true;

        for (final Input input : textInputs.keySet()) {
            int error = InputValidator.validateText(textInputs.get(input));
            if (error != 0) {
                errorListener.processError(input, error);
                isValid = false;
            }
        }

        for (final Input input : phoneNumberInputs.keySet()) {
            int error = InputValidator.validatePhoneNumber(phoneNumberInputs.get(input));
            if (error != 0) {
                errorListener.processError(input, error);
                isValid = false;
            }
        }

        for (final Input input : passwordInputs.keySet()) {
            int error = InputValidator.validatePassword(passwordInputs.get(input));
            if (error != 0) {
                errorListener.processError(input, error);
                isValid = false;
            }
        }

        for (final Pair<Input, Input> pair : passwordConfirmationInputs.keySet()) {
            final String password = passwordInputs.get(pair.first);
            final String confirmation = passwordConfirmationInputs.get(pair);

            int error = InputValidator.validatePasswordConfirmation(password, confirmation);
            if (error != 0) {
                errorListener.processError(pair.second, error);
                isValid = false;
            }
        }

        return isValid;
    }

    public static int validateText(final String text) {
        if (text == null || text.trim().isEmpty())
            return R.string.blank_text_validation_error;

        return 0;
    }

    public static int validatePhoneNumber(final String phoneNumber) {
        int length;
        if (phoneNumber == null || (length = phoneNumber.trim().length()) == 0)
            return R.string.blank_text_validation_error;

        final int compared = PhoneNumberRange.compareTo(length);
        if (compared > 0)
            return R.string.too_short_validation_error;
        else if (compared < 0)
            return R.string.too_long_validation_error;

        return 0;
    }

    public static int validatePassword(final String password) {
        return validateText(password);
    }

    public static int validatePasswordConfirmation(
        final String password, final String confirmation
    ) {
        if (password == null || !password.equals(confirmation))
            return R.string.password_confirmation_validation_error;

        return 0;
    }
}
