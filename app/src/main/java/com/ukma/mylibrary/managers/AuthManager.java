package com.ukma.mylibrary.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.entities.factory.EntityFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class AuthManager {
    private static AuthManager authManager = null;
    private static final String S_PREF_TOKEN_ID = "com.ukma.mylibrary.S_PREF_TOKEN_ID";
    private static final String TOKEN_KEY = "TOKEN_KEY";
    public static User CURRENT_USER;
    public static String JWT_TOKEN;
    private Context context;
    private SharedPreferences sPref;

    private AuthManager(Context context) {
        this.context = context;
        loadToken();
    }

    private void loadToken() {
        sPref = context.getSharedPreferences(S_PREF_TOKEN_ID, Context.MODE_PRIVATE);
        JWT_TOKEN = sPref.getString(TOKEN_KEY, null);
    }

    private void saveToken(String token) {
        sPref = context.getSharedPreferences(S_PREF_TOKEN_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(TOKEN_KEY, token);
        ed.commit();
        JWT_TOKEN = token;
    }

    public static AuthManager getManager(Context context) {
        if (authManager == null) {
            authManager = new AuthManager(context);
            return authManager;
        }

        if (CURRENT_USER == null && JWT_TOKEN != null) {
            authManager.fetchCurrentUser(
             new APIResponse.Listener<User>() {
                 @Override
                 public void onResponse(final User __) {}
             }, new APIResponse.ErrorListener() {
                 @Override
                 public void onErrorResponse(final VolleyError error) {
                     Log.e(AuthManager.class.getSimpleName(), error.getMessage(), error);
                     authManager.saveToken(null);
                 }
             });
        }

        authManager.context = context;

        return authManager;
    }

    public boolean isAuthenticated() {
        return CURRENT_USER != null && JWT_TOKEN != null;
    }

    public void fetchCurrentUser(final APIResponse.Listener<User> responseListener,
                                 final APIResponse.ErrorListener responseErrorListener) {
        try {
            API.call(Route.GetCurrentUser, User.class)
                .then(new APIResponse.Listener<User>() {
                    @Override
                    public void onResponse(final User user) {
                        CURRENT_USER = user;
                        responseListener.onResponse(CURRENT_USER);
                    }
                })
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public void signIn(String phone_num, String password,
                       final APIResponse.Listener<User> responseListener,
                       APIResponse.ErrorListener responseErrorListener) {
        JSONObject userCredentialsObject = new JSONObject();
        try {
            userCredentialsObject.put("phone_num", phone_num);
            userCredentialsObject.put("password", password);

            API.call(Route.SignIn)
                .body("user", userCredentialsObject)
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

    public void signUp(final User user, final APIResponse.Listener<User> responseListener,
                       final APIResponse.ErrorListener responseErrorListener) {
        try {
            API.call(Route.SignUp)
                .body("user", user)
                .then(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        signIn(
                            user.getPhoneNum(),
                            user.getPassword(),
                            responseListener,
                            responseErrorListener
                        );
                    }
                })
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public void signOut(
        final APIResponse.Listener<JSONObject> responseListener,
        APIResponse.ErrorListener responseErrorListener) {
        try {
            API.call(Route.SignOut)
                .then(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        saveToken(null);
                        responseListener.onResponse(response);
                    }
                })
                .catchError(responseErrorListener)
                .executeWithContext(context);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }
}
