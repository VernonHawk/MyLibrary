package com.ukma.mylibrary;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.components.LibrarianItem;
import com.ukma.mylibrary.components.LibrarianReturnItem;
import com.ukma.mylibrary.components.LibrarianWithdrawalItem;
import com.ukma.mylibrary.entities.Role;
import com.ukma.mylibrary.entities.SciPubOrder;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.helpers.TestsHelper;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.matchers.LibrarianItemMatchers;
import com.ukma.mylibrary.matchers.LibrarianReturnItemMatchers;
import com.ukma.mylibrary.matchers.LibrarianWithdrawalItemMatchers;
import com.ukma.mylibrary.tools.Fetcher;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.pressBack;
import static com.ukma.mylibrary.helpers.TestsHelper.clickOn;
import static com.ukma.mylibrary.helpers.TestsHelper.clickOnChild;
import static com.ukma.mylibrary.helpers.TestsHelper.inputText;
import static com.ukma.mylibrary.helpers.TestsHelper.onFirstListItem;
import static com.ukma.mylibrary.helpers.TestsHelper.onListItem;


@RunWith(AndroidJUnit4.class)
public class LibrarianAcceptanceTest {

    private static final User mReader = new User("", "", "11111111", Role.Reader, "TEST");

    private static final String mLibrarianPhoneNumber = "12345678";
    private static final String mLibrarianPassword = "secret";

    private static final long mOrderedSciPubId = 1;
    private static String mOrderedSciPubName = "";

    private static final String mSearch = "1111";

    @ClassRule
    public static ActivityTestRule<MainActivity> mClassRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setupReader() {
        final Context context = mClassRule.getActivity();
        final AuthManager authManager = AuthManager.getManager(context);

        final APIResponse.ErrorListener errorListener = new APIResponse.ErrorListener() {
            @Override public void onErrorResponse(final VolleyError error) {
                Log.e(LibrarianAcceptanceTest.class.getSimpleName(), error.getMessage());
            }
        };

        authManager.signIn(
            mReader.getPhoneNum(), mReader.getPassword(),
            new APIResponse.Listener<User>() {
                @Override public void onResponse(final User response) {
                    mReader.setName(response.getName());
                    mReader.setSurname(response.getSurname());

                    Fetcher.orderSciPub(
                        context, AuthManager.CURRENT_USER.getId(), mOrderedSciPubId,
                        new APIResponse.Listener<SciPubOrder>() {
                            @Override
                            public void onResponse(final SciPubOrder response) {
                                mOrderedSciPubName = response.getScientificPublication().getName();
                                authManager.signOut(null, errorListener);
                            }
                        },
                        errorListener
                    );
                }
            },
            errorListener
        );
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void signIn_Search_Withdraw_Return() {
        // choose role and go to sign in page
        clickOn(R.id.librarian);
        clickOn(R.id.continue_button);

        // fill the form
        inputText(R.id.input_phone, mLibrarianPhoneNumber);
        inputText(R.id.input_password, mLibrarianPassword);

        // sign in
        clickOn(R.id.sign_in_btn);
        TestsHelper.wait(1500);

        // search for the reader
        inputText(R.id.librarian_userlist_search_view, mSearch);
        clickOn(R.id.librarian_userlist_search_btn);

        // open reader's withdraw page
        TestsHelper.wait(100);

        clickOnChild(
            onListItem(LibrarianItem.class, LibrarianItemMatchers.withFullName(mReader.getFullName())),
            R.id.list_readers_withdraw_btn
        );

        // give sci pub
        TestsHelper.wait(500);

        clickOnChild(
            onFirstListItem(
                LibrarianWithdrawalItem.class,
                LibrarianWithdrawalItemMatchers.withName(mOrderedSciPubName)
            ),
            R.id.list_librarian_withdrawal_withdrawal_approve
        );

        // return
        pressBack();

        // open reader's return page
        clickOnChild(
            onListItem(LibrarianItem.class, LibrarianItemMatchers.withFullName(mReader.getFullName())),
            R.id.list_readers_return_btn
        );

        // return sci pub
        TestsHelper.wait(500);

        clickOnChild(
            onFirstListItem(
                LibrarianReturnItem.class,
                LibrarianReturnItemMatchers.withName(mOrderedSciPubName)
            ),
            R.id.list_librarian_return_return_approve
        );

        // return and sign out
        pressBack();
        pressBack();
    }
}
