package com.ukma.mylibrary.api;

import android.content.Context;

import com.android.volley.Response;
import com.ukma.mylibrary.managers.RequestQueueManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class APIRequest {
    private String path;
    private int method;
    private Map<String, String> routeParams = null;
    private Map<String, String> queryParams = null;
    private JSONObject requestObject = null;
    private JSONArray requestArray = null;
    private Response.Listener<JSONObject> responseObjectListener = null;
    private Response.Listener<JSONArray> responseArrayListener = null;
    private Response.ErrorListener responseErrorListener = null;

    public APIRequest(String path, int method) {
        this.path = path;
        this.method = method;
    }

    public APIRequest params(Map<String, String> routeParams) {
        for (Map.Entry<String, String> entry : routeParams.entrySet()) {
            this.path = this.path.replaceFirst("\\{" + entry.getKey() + "\\}", entry.getValue());
        }
        return this;
    }

    public APIRequest body(JSONObject requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    public APIRequest body(JSONArray requestArray) {
        this.requestArray = requestArray;
        return this;
    }

    public APIRequest query(Map<String, String> queryParams) {
        this.path += "?";
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            this.path += entry.getKey() + "=" + entry.getValue() + "&";
        }
        this.path = this.path.substring(0, this.path.length() - 1);
        return this;
    }

    public APIRequest then(Response.Listener<JSONObject> responseObjectListener) {
        this.responseObjectListener = responseObjectListener;
        return this;
    }

    public APIRequest thenWithArray(Response.Listener<JSONArray> responseArrayListener) {
        this.responseArrayListener = responseArrayListener;
        return this;
    }

    public APIRequest catchError(Response.ErrorListener responseErrorListener) {
        this.responseErrorListener = responseErrorListener;
        return this;
    }

    public void executeWithContext(Context context) throws APIRequestNoListenerSpecifiedException {
        if (this.responseObjectListener != null) {
            APIJsonObjectRequest apiJsonObjectRequest = new APIJsonObjectRequest(
                    this.method,
                    this.path,
                    this.requestObject,
                    this.responseObjectListener,
                    this.responseErrorListener
            );
            RequestQueueManager.getInstance(context).addToRequestQueue(apiJsonObjectRequest);
        } else if (this.responseArrayListener != null) {
            APIJsonArrayRequest apiJsonArrayRequest = new APIJsonArrayRequest(
                    this.method,
                    this.path,
                    this.requestArray,
                    this.responseArrayListener,
                    this.responseErrorListener
            );
            RequestQueueManager.getInstance(context).addToRequestQueue(apiJsonArrayRequest);
        } else {
            throw new APIRequestNoListenerSpecifiedException("No listener specified!!! Use `then` or `thenWithArray` method.");
        }
    }

    public class APIRequestNoListenerSpecifiedException extends Exception {
        public APIRequestNoListenerSpecifiedException(String message) {
            super(message);
        }
    }
}
