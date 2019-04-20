package com.ukma.mylibrary;

import android.support.test.espresso.DataInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ukma.mylibrary.components.LibraryItem;
import com.ukma.mylibrary.components.ReservedReaderItem;
import com.ukma.mylibrary.helpers.TestsHelper;
import com.ukma.mylibrary.matchers.LibraryItemMatchers;
import com.ukma.mylibrary.matchers.ReservedReaderItemMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static com.ukma.mylibrary.helpers.TestsHelper.clickOn;
import static com.ukma.mylibrary.helpers.TestsHelper.clickOnChild;
import static com.ukma.mylibrary.helpers.TestsHelper.inputText;
import static com.ukma.mylibrary.helpers.TestsHelper.onFirstListItem;
import static com.ukma.mylibrary.helpers.TestsHelper.onListItem;


@RunWith(AndroidJUnit4.class)
public class ReaderAcceptanceTest {

    private static final String mReaderPhoneNumber = "11111111";
    private static final String mReaderPassword = "TEST";
    private static final String mSearch = "A ";
    private static final String mFirstBookName = "A Scanner Darkly";
    private static final String mSecondBookName = "A Time of Gifts";

    @Rule public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void signIn_OrderTwoSciPubs_CancelBoth() {
        // go to sign in page
        clickOn(R.id.continue_button);

        // fill the form
        inputText(R.id.input_phone, mReaderPhoneNumber);
        inputText(R.id.input_password, mReaderPassword);

        // sign in
        clickOn(R.id.sign_in_btn);
        TestsHelper.wait(1500);

        // go to library
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        clickOn("Library");

        // search for a sci pub
        inputText(R.id.search_view, mSearch);
        clickOn(R.id.sci_pub_search_btn);

        TestsHelper.wait(1000);

        // order two sci pubs
        clickOnChild(
            onListItem(LibraryItem.class, LibraryItemMatchers.withName(mFirstBookName)),
            R.id.library_item_take_order_btn);

        clickOnChild(
            onListItem(LibraryItem.class, LibraryItemMatchers.withName(mSecondBookName)),
            R.id.library_item_take_order_btn);

        // go to orders
        clickOn(R.id.action_orders);
        clickOn(R.id.rb_reserved);

        TestsHelper.wait(1000);

        // see that sci pubs are here and have order date as today
        final DataInteraction firstOrder = onFirstListItem(
            ReservedReaderItem.class,
            ReservedReaderItemMatchers.withName(mFirstBookName),
            ReservedReaderItemMatchers.withOrderDate(new Date())
        );

        final DataInteraction secondOrder = onFirstListItem(
            ReservedReaderItem.class,
            ReservedReaderItemMatchers.withName(mSecondBookName),
            ReservedReaderItemMatchers.withOrderDate(new Date())
        );

        // cancel them
        clickOnChild(firstOrder, R.id.item_reserved_cancel_order_btn);
        TestsHelper.wait(500);
        clickOnChild(secondOrder, R.id.item_reserved_cancel_order_btn);

        pressBack();
    }
}
