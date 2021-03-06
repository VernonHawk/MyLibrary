package com.ukma.mylibrary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.ToastHelper;

import java.net.HttpURLConnection;

public class ToolbarActivity extends AppCompatActivity {
    protected void signOut() {
        AuthManager.getManager(this).signOut(null, new APIResponse.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                ToastHelper.show(ToolbarActivity.this, R.string.sign_out_error_message);
            }
        });
    }

    protected void handleError(final VolleyError error, final Context context) {
        Log.e(getClass().getSimpleName(), error.getMessage(), error);

        final Pair<APIResponse.Error, Integer> errWithMsg = APIResponse.handleError(error);
        final APIResponse.Error err = errWithMsg.first;

        if (err == null) {
            ToastHelper.show(context, R.string.some_error_message);
        } else if (err.status() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            ToastHelper.show(context, errWithMsg.second);
            signOut();
        } else {
            ToastHelper.show(context, R.string.some_error_message);
        }
    }
}