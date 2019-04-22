package com.ukma.mylibrary.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.ukma.mylibrary.components.LibrarianWithdrawalItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class LibrarianWithdrawalItemMatchers {
    public static Matcher<Object> withName(final String name) {
        return new BoundedMatcher<Object, LibrarianWithdrawalItem>(LibrarianWithdrawalItem.class) {
            @Override
            public boolean matchesSafely(final LibrarianWithdrawalItem item) {
                return item.getPublicationName().equals(name);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(String.format("with name '%s'", name));
            }
        };
    }
}
