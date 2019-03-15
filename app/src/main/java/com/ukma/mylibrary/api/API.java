package com.ukma.mylibrary.api;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ukma.mylibrary.managers.RequestQueueManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class API {
    private final static String BASE_PATH = "https://mylibraryapplication.herokuapp.com";

    private static String normalizeRoutePath(String route) {
        if (route.charAt(0) == '/') {
            return BASE_PATH + route;
        }
        return BASE_PATH + "/" + route;
    }

    public static void call(Activity context, String route, Response.Listener<JSONObject> responseListener,
                            Response.ErrorListener responseErrorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.GET,
            normalizeRoutePath(route),
            null,
            responseListener,
            responseErrorListener
        );
        RequestQueueManager.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void call(Activity context, String route, int method, JSONObject requestObject, Response.Listener<JSONObject> responseListener,
                            Response.ErrorListener responseErrorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                method,
                normalizeRoutePath(route),
                requestObject,
                responseListener,
                responseErrorListener
        );
        RequestQueueManager.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void callWithArray(Activity context,  String route, Response.Listener<JSONArray> responseListener,
                                     Response.ErrorListener responseErrorListener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            normalizeRoutePath(route),
            null,
            responseListener,
            responseErrorListener
        );
        RequestQueueManager.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void callWithArray(Activity context, String route, int method, JSONArray requestArray, Response.Listener<JSONArray> responseListener,
                            Response.ErrorListener responseErrorListener) {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
            method,
            normalizeRoutePath(route),
            requestArray,
            responseListener,
            responseErrorListener
        );
        RequestQueueManager.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
