package com.ukma.mylibrary.api;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ukma.mylibrary.Tools.JWTHeaderHelper;

import org.json.JSONObject;

import java.util.Map;

public class APIJsonObjectRequest extends JsonObjectRequest {
    public APIJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest,
                                Response.Listener<JSONObject> listener,
                                @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public APIJsonObjectRequest(String url, @Nullable JSONObject jsonRequest,
                                Response.Listener<JSONObject> listener,
                                @Nullable Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return JWTHeaderHelper.createHeader();
    }
}
