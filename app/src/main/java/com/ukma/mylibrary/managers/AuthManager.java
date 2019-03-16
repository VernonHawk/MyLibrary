package com.ukma.mylibrary.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequest;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.entities.factory.EntityFactory;
import com.ukma.mylibrary.entities.factory.EntityJSONFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class AuthManager {
    private static AuthManager authManager = null;
    private static final String S_PREF_TOKEN_ID = "com.ukma.mylibrary.S_PREF_TOKEN_ID";
    private static final String TOKEN_KEY = "TOKEN_KEY";
    public static String JWT_TOKEN;
    private Context context;
    private User currentUser;
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

    public User getCurrentUser() {
        return currentUser;
    }

    public static AuthManager getManager(Context context) {
        if (authManager == null) {
            authManager = new AuthManager();
            authManager.context = context;
            authManager.loadToken();
            return authManager;
        }
        authManager.context = context;
        return authManager;
    }

    public void signIn(String phone_num, String password,
                       final Response.Listener<JSONObject> responseListener,
                       Response.ErrorListener responseErrorListener) {
        JSONObject requestObject = new JSONObject();
        JSONObject userCredentialsObject = new JSONObject();
        try {
            userCredentialsObject.put("phone_num", phone_num);
            userCredentialsObject.put("password", password);
            requestObject.put("user", userCredentialsObject);

            API.call(Route.SignIn)
                .body(requestObject)
                .then(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        EntityFactory entityFactory = new EntityFactory();
                        try {
                            String token = (String) response.get("access_token");
                            saveToken(token);
                            currentUser = (User) entityFactory.getEntity(response.getString("user"), User.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        responseListener.onResponse(response);
                    }
                })
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (APIRequest.APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public void signUp(User user, Response.Listener<JSONObject> responseListener,
                       Response.ErrorListener responseErrorListener) {
        EntityJSONFactory entityJSONFactory = new EntityJSONFactory();
        JSONObject userJSONObject = entityJSONFactory.getEntityJSON(user);
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("user", userJSONObject);

            API.call(Route.SignUp)
                .then(responseListener)
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (APIRequest.APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public void signOut(Response.Listener<JSONObject> responseListener,
                        Response.ErrorListener responseErrorListener) {
        // TODO ...
    }

}
