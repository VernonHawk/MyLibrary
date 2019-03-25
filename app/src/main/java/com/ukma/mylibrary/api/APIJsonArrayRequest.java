package com.ukma.mylibrary.api;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ukma.mylibrary.tools.JWTHeaderHelper;

import org.json.JSONArray;

import java.util.Map;

public class APIJsonArrayRequest extends JsonArrayRequest {
    public APIJsonArrayRequest(String url, Response.Listener<JSONArray> listener,
                               @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public APIJsonArrayRequest(int method, String url, @Nullable JSONArray jsonRequest,
                               Response.Listener<JSONArray> listener,
                               @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() {
        return JWTHeaderHelper.createHeader();
    }
}
