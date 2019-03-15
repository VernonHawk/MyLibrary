package com.ukma.mylibrary.managers;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.entities.factory.EntityFactory;
import com.ukma.mylibrary.entities.factory.EntityJSONFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class AuthManager {
    private static final String SIGN_UP_URL = "/register";
    private static final String SIGN_IN_URL = "/login";

    private static AuthManager authManager = null;
    private User currentUser;

    private AuthManager() {}

    public User getCurrentUser() {
        return currentUser;
    }

    public static AuthManager getManager() {
        if (authManager == null) {
            authManager = new AuthManager();
        }
        return authManager;
    }

    public void signIn(Activity context, String phone_num, String password,
                       final Response.Listener<JSONObject> responseListener,
                       Response.ErrorListener responseErrorListener) {
        JSONObject requestObject = new JSONObject();
        JSONObject userCredentialsObject = new JSONObject();
        try {
            userCredentialsObject.put("phone_num", phone_num);
            userCredentialsObject.put("password", password);
            requestObject.put("user", userCredentialsObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.call(context, SIGN_IN_URL, Request.Method.POST, requestObject,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    EntityFactory entityFactory = new EntityFactory();
                    currentUser = (User) entityFactory.getEntity(response, User.class);
                    responseListener.onResponse(response);
                }
            },
            responseErrorListener
        );
    }

    public void signUp(Activity context, User user, Response.Listener<JSONObject> responseListener,
                       Response.ErrorListener responseErrorListener) {
        EntityJSONFactory entityJSONFactory = new EntityJSONFactory();
        JSONObject userJSONObject = entityJSONFactory.getEntityJSON(user);
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("user", userJSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.call(
            context,
            SIGN_UP_URL,
            Request.Method.POST,
            requestObject,
            responseListener,
            responseErrorListener
        );
    }

    public void signOut(Activity context, Response.Listener<JSONObject> responseListener,
                        Response.ErrorListener responseErrorListener) {
        // TODO ...
    }

}
