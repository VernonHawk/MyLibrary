package com.ukma.mylibrary.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.ukma.mylibrary.components.LibraryItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class LibraryItemMatchers {
    public static Matcher<Object> withName(final String name) {
        return new BoundedMatcher<Object, LibraryItem>(LibraryItem.class) {
            @Override
            public boolean matchesSafely(final LibraryItem item) {
                return item.getName().equals(name);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(String.format("with name '%s'", name));
            }
        };
    }
}
