package com.ukma.mylibrary.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.ukma.mylibrary.components.ReservedReaderItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Date;

public class ReservedReaderItemMatchers {
    public static Matcher<Object> withName(final String name) {
        return new BoundedMatcher<Object, ReservedReaderItem>(ReservedReaderItem.class) {
            @Override
            public boolean matchesSafely(final ReservedReaderItem item) {
                return item.getName().equals(name);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(String.format("with name '%s'", name));
            }
        };
    }

    public static Matcher<Object> withOrderDate(final Date date) {
        return new BoundedMatcher<Object, ReservedReaderItem>(ReservedReaderItem.class) {
            @Override
            public boolean matchesSafely(final ReservedReaderItem item) {
                final boolean sameDay   = date.getDay()   == item.getOrderDate().getDay();
                final boolean sameMonth = date.getMonth() == item.getOrderDate().getMonth();
                final boolean sameYear  = date.getYear()  == item.getOrderDate().getYear();

                return sameDay && sameMonth && sameYear;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(String.format("with order date '%s'", date));
            }
        };
    }
}