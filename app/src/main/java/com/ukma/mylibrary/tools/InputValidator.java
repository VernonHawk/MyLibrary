package com.ukma.mylibrary.tools;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Pair;

import com.ukma.mylibrary.R;

import java.util.Collections;
import java.util.HashMap;
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

    /**
     * Interface representing a form input
     */
    public interface Input {

        /**
         @return name suitable for displaying to a user
         */
        String canonicalName();
    }

    public interface InvalidInputListener {
        void processError(final Input input, final int errStringId);
    }

    private static final Range PhoneNumberRange = new Range<>(8, 20);

    // region areInputsValid
    /**
     Validates all passed inputs as text inputs

     @param inputToLayout Inputs bound to their TextInputLayouts
     @param texts         Inputs and their values to be validated as text
     @param errorListener listener to call when an input is invalid

     @return are all passed inputs valid
     */
    public static boolean areInputsValid(
        @NonNull final Map<Input, TextInputLayout> inputToLayout,
        final Map<Input, String> texts,
        final InvalidInputListener errorListener
    ) {
        Map<Input, String>              phoneNumbers          = Collections.emptyMap();
        Map<Input, String>              passwords             = Collections.emptyMap();
        Map<Pair<Input, Input>, String> passwordConfirmations = Collections.emptyMap();

        return areInputsValid(
            inputToLayout, texts, phoneNumbers, passwords, passwordConfirmations, errorListener
        );
    }

    /**
     Validates all passed inputs accordingly to their types

     @param inputToLayout Inputs bound to their TextInputLayouts
     @param texts         Inputs and their values to be validated as text
     @param phoneNumber   Input and it's value to be validated as phone number
     @param password      Input and it's value to be validated as password
     @param passwordConfirmation Pair of password and confirmation Inputs and confirmation value
                                 to be validated as password confirmation
     @param errorListener listener to call when an input is invalid

     @return are all passed inputs valid
     */
    public static boolean areInputsValid(
        @NonNull final Map<Input, TextInputLayout> inputToLayout,
        @NonNull final Map<Input, String> texts,
        @NonNull final Pair<Input, String> phoneNumber,
        @NonNull final Pair<Input, String> password,
        @NonNull final Pair<Input, String> passwordConfirmation,
        final InvalidInputListener errorListener
    ) {
        Map<Input, String> phoneNumbers = new HashMap<Input, String>() {{
            put(phoneNumber.first, phoneNumber.second);
        }};

        Map<Input, String> passwords = new HashMap<Input, String>() {{
            put(password.first, password.second);
        }};

        Map<Pair<Input, Input>, String> passwordConfirmations = new HashMap<Pair<Input, Input>, String>() {{
            put(new Pair<>(password.first, passwordConfirmation.first), passwordConfirmation.second);
        }};

        return areInputsValid(
            inputToLayout, texts, phoneNumbers, passwords, passwordConfirmations, errorListener
        );
    }

    /**
     Validates all passed inputs accordingly to their types

     @param inputToLayout Inputs bound to their TextInputLayouts
     @param texts         Inputs and their values to be validated as text
     @param phoneNumbers  Inputs and their values to be validated as phone number
     @param passwords     Inputs and their values to be validated as password
     @param passwordConfirmations Pairs of password and confirmation Inputs and confirmation values
                                  to be validated as password confirmation
     @param errorListener listener to call when an Inputs is invalid

     @return are all passed inputs valid
     */
    public static boolean areInputsValid(
        @NonNull final Map<Input, TextInputLayout> inputToLayout,
        final Map<Input, String> texts,
        final Map<Input, String> phoneNumbers,
        final Map<Input, String> passwords,
        final Map<Pair<Input, Input>, String> passwordConfirmations,
        final InvalidInputListener errorListener
    ) {
        // clear errors
        for (final TextInputLayout layout : inputToLayout.values())
            layout.setError(null);

        boolean isValid = true;

        for (final Input input : texts.keySet()) {
            final int error = validateText(texts.get(input));
            if (error != 0) {
                errorListener.processError(input, error);
                isValid = false;
            }
        }

        for (final Input input : phoneNumbers.keySet()) {
            final int error = validatePhoneNumber(phoneNumbers.get(input));
            if (error != 0) {
                errorListener.processError(input, error);
                isValid = false;
            }
        }

        for (final Input input : passwords.keySet()) {
            final int error = validatePassword(passwords.get(input));
            if (error != 0) {
                errorListener.processError(input, error);
                isValid = false;
            }
        }

        for (final Pair<Input, Input> pair : passwordConfirmations.keySet()) {
            final String password = passwords.get(pair.first);
            final String confirmation = passwordConfirmations.get(pair);

            final int error = validatePasswordConfirmation(password, confirmation);
            if (error != 0) {
                errorListener.processError(pair.second, error);
                isValid = false;
            }
        }

        return isValid;
    }
    // endregion

    // region Validators
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
    // endregion
}
