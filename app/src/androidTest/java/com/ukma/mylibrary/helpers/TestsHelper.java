package com.ukma.mylibrary.helpers;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class TestsHelper {

    public static ViewInteraction inputText(final int viewId, final String text) {
        final ViewInteraction interaction = onView(withId(viewId)).perform(typeText(text));
        pressBack();

        return interaction;
    }

    public static ViewInteraction clickOn(final int viewId) {
        return onView(withId(viewId)).perform(click());
    }

    public static ViewInteraction clickOn(final String text) {
        return onView(withText(text)).perform(click());
    }

    public static ViewInteraction clickOnChild(
        final DataInteraction interaction, final int childViewId
    ) {
        return interaction.onChildView(withId(childViewId)).perform(click());
    }

    public static ViewInteraction wait(final int millis) {
        return onView(isRoot()).perform(ViewActions.waitFor(millis));
    }

    public static DataInteraction onFirstListItem(
        final Class itemClass, final Matcher<Object> matcher
    ) {
        return onListItem(itemClass, matcher).atPosition(0);
    }

    public static DataInteraction onFirstListItem(
        final Class itemClass, final Matcher<Object> matcher1, final Matcher<Object> matcher2
    ) {
        return onListItem(itemClass, matcher1, matcher2).atPosition(0);
    }

    public static DataInteraction onListItem(final Class itemClass, final Matcher<Object> matcher) {
        return onData(allOf(is(instanceOf(itemClass)), matcher));
    }

    public static DataInteraction onListItem(
        final Class itemClass, final Matcher<Object> matcher1, final Matcher<Object> matcher2
    ) {
        return onData(allOf(instanceOf(itemClass), matcher1, matcher2));
    }
}
