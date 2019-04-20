package com.ukma.mylibrary.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.ukma.mylibrary.components.LibrarianItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class LibrarianItemMatchers {
    public static Matcher<Object> withFullName(final String fullName) {
        return new BoundedMatcher<Object, LibrarianItem>(LibrarianItem.class) {
            @Override
            public boolean matchesSafely(final LibrarianItem item) {
                return item.getUser().getFullName().equals(fullName);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(String.format("with full name '%s'", fullName));
            }
        };
    }
}
