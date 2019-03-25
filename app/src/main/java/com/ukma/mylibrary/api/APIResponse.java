package com.ukma.mylibrary.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.R;
import com.ukma.mylibrary.tools.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class APIResponse {
    /**
     * Callback interface for delivering parsed responses.
     */
    public interface Listener<T> {
        /**
         * Called when a response is received.
         */
        void onResponse(T response);
    }

    /**
     * Callback interface for delivering error responses.
     */
    public interface ErrorListener {
        /**
         * Callback method that an error has been occurred with the provided error code and optional
         * user-readable message.
         */
        void onErrorResponse(VolleyError error);
    }

    public static Error handleError(final Context context, final VolleyError error) {
        Log.e(context.getClass().getSimpleName(), "", error);

        final APIResponse.Error err = APIResponse.getError(error.networkResponse);

        switch (error.networkResponse.statusCode) {
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                ToastHelper.show(context, R.string.internal_error_message);
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                if (err == null)
                    ToastHelper.show(context, R.string.auth_fail_message);
                break;
            default:
                ToastHelper.show(context, R.string.some_error_message);
        }

        return err;
    }

    public static Error getError(final NetworkResponse response) {
        try {
            return new Error(parseBody(response).getJSONArray("errors").getJSONObject(0));
        } catch (final JSONException e) {
            Log.e(APIResponse.class.getSimpleName(), "Couldn't get error from response.", e);
            return null;
        }
    }

    public static JSONObject parseBody(final NetworkResponse response) throws JSONException {
        return new JSONObject(new String(response.data));
    }

    public static class Error {
        private String status;
        private String title;
        private JSONObject detail;

        Error(final JSONObject error) throws JSONException {
            status = error.getString("status");
            title = error.getString("title");
            detail = error.getJSONObject("detail");
        }

        public String status() {
            return status;
        }

        public String title() {
            return title;
        }

        public JSONObject detail() {
            return detail;
        }
    }
}
