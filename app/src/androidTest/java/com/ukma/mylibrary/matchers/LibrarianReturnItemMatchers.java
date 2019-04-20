package com.ukma.mylibrary.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.ukma.mylibrary.components.LibrarianReturnItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class LibrarianReturnItemMatchers {
    public static Matcher<Object> withName(final String name) {
        return new BoundedMatcher<Object, LibrarianReturnItem>(LibrarianReturnItem.class) {
            @Override
            public boolean matchesSafely(final LibrarianReturnItem item) {
                return item.getPublicationName().equals(name);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(String.format("with name '%s'", name));
            }
        };
    }
}
