package com.ukma.mylibrary;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.ToastHelper;

import org.json.JSONObject;

public class ToolbarActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        signOut();
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

    public void formatLabelWithRole(final int viewId, final int stringId) {

//        ((TextView) findViewById(viewId)).setText(
//                String.format(getString(stringId), mUserRole.name())
//        );
//        formatLabelWithRole(R.id.hello_label, R.string.hello_user_role);
    }

}