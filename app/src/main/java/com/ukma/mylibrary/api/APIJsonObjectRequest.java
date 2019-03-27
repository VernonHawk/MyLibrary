package com.ukma.mylibrary.api;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ukma.mylibrary.tools.JWTHeaderHelper;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
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
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        if (response.data.length == 0) {
            final byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
            response = new NetworkResponse(
                response.statusCode, responseData, response.headers, response.notModified
            );
        }

        return super.parseNetworkResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() {
        return JWTHeaderHelper.createHeader();
    }
}
