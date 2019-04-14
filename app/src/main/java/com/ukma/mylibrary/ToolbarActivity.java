package com.ukma.mylibrary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.ToastHelper;

import org.json.JSONObject;

import java.net.HttpURLConnection;

public class ToolbarActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        signOut();
    }
  
    public void formatLabelWithUsername(final int viewId, final int stringId) {

      //TODO username in %s
    }

    protected void signOut() {
        AuthManager.getManager(this).signOut(new APIResponse.Listener<JSONObject>() {
            @Override
            public void onResponse(@SuppressWarnings("unused") final JSONObject __) {
                finish();
            }
        }, new APIResponse.ErrorListener() {
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