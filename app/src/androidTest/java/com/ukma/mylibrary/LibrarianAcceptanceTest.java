package com.ukma.mylibrary;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.entities.SciPubOrder;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.Fetcher;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class LibrarianAcceptanceTest {

    private static final String mReaderPhoneNumber = "11111111";
    private static final String mReaderPassword = "TEST";
    private static final long mOrderedSciPubId = 1;

    @ClassRule
    public static ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setupReader() {
        final Context context = mActivityRule.getActivity();
        final AuthManager authManager = AuthManager.getManager(context);

        final APIResponse.ErrorListener errorListener = new APIResponse.ErrorListener() {
            @Override public void onErrorResponse(final VolleyError error) {
                Log.e(LibrarianAcceptanceTest.class.getSimpleName(), error.getMessage());
            }
        };

        authManager.signIn(
            mReaderPhoneNumber, mReaderPassword,
            new APIResponse.Listener<User>() {
                @Override public void onResponse(final User response) {
                    Fetcher.orderSciPub(
                        context, AuthManager.CURRENT_USER.getId(), mOrderedSciPubId,
                        new APIResponse.Listener<SciPubOrder>() {
                            @Override
                            public void onResponse(final SciPubOrder __) {
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

    @Test
    public void signIn_Search_Withdraw_Return() {

    }
}
