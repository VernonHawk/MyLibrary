package com.ukma.mylibrary.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequest;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.entities.Entity;
import com.ukma.mylibrary.entities.Role;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.entities.factory.EntityFactory;
import com.ukma.mylibrary.entities.factory.EntityJSONFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuthManager {
    private static AuthManager authManager = null;
    private static final String S_PREF_TOKEN_ID = "com.ukma.mylibrary.S_PREF_TOKEN_ID";
    private static final String TOKEN_KEY = "TOKEN_KEY";
    public static User CURRENT_USER;
    public static String JWT_TOKEN;
    private Context context;
    private SharedPreferences sPref;

    private AuthManager() {}

    private void loadToken() {
        sPref = context.getSharedPreferences(S_PREF_TOKEN_ID, context.MODE_PRIVATE);
        JWT_TOKEN = sPref.getString(TOKEN_KEY, null);
    }

    private void saveToken(String token) {
        sPref = context.getSharedPreferences(S_PREF_TOKEN_ID, context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(TOKEN_KEY, token);
        ed.commit();
        JWT_TOKEN = token;
    }

    public static AuthManager getManager(Context context) {
        if (authManager == null) {
            authManager = new AuthManager();
            authManager.context = context;
            // TODO
//            authManager.loadToken();
            return authManager;
        }
        authManager.context = context;
        return authManager;
    }

    public void signIn(String phone_num, String password,
                       final APIResponse.Listener<User> responseListener,
                       APIResponse.ErrorListener responseErrorListener) {
        JSONObject requestObject = new JSONObject();
        JSONObject userCredentialsObject = new JSONObject();
        try {
            userCredentialsObject.put("phone_num", phone_num);
            userCredentialsObject.put("password", password);
            requestObject.put("user", userCredentialsObject);

            new API().call(Route.SignIn)
                .body(requestObject)
                .then(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        EntityFactory entityFactory = new EntityFactory();
                        try {
                            String token = (String) response.get("access_token");
                            saveToken(token);
                            CURRENT_USER = (User) entityFactory.getEntity(response.getString("user"), User.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        responseListener.onResponse(CURRENT_USER);
                    }
                })
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public void signUp(User user, APIResponse.Listener<User> responseListener,
                       APIResponse.ErrorListener responseErrorListener) {
        EntityJSONFactory entityJSONFactory = new EntityJSONFactory();
        JSONObject userJSONObject = entityJSONFactory.getEntityJSON(user);
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("user", userJSONObject);

            new API().call(Route.SignUp)
                .then(responseListener)
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public void signOut(Response.Listener<JSONObject> responseListener,
                        Response.ErrorListener responseErrorListener) {
        // TODO ...
    }

}