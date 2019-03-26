package com.ukma.mylibrary.api;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.R;

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

    public interface ErrorIdentifiedListener {
        /**
         * Callback method that an error has been identified Error object and error message id
         */
        void onErrorIdentified(Error error, int msgId);
    }


    public static void handleError(
        final VolleyError error, final ErrorIdentifiedListener listener
    ) {
        final Error err = APIResponse.getError(error.networkResponse);

        switch (error.networkResponse.statusCode) {
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                listener.onErrorIdentified(err, R.string.internal_error_message);
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                listener.onErrorIdentified(err, R.string.auth_fail_message);
                break;
            case HttpURLConnection.HTTP_BAD_REQUEST:
                listener.onErrorIdentified(err, R.string.bad_request_message);
                break;
            default:
                listener.onErrorIdentified(err, R.string.some_error_message);
        }
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
        private int status;
        private String title;
        private JSONObject detail;

        Error(final JSONObject error) throws JSONException {
            try {
                status = Integer.parseInt(error.getString("status"));
            } catch (final NumberFormatException nfe) {
                status = -1;
            }
            title  = error.getString("title");
            detail = error.getJSONObject("detail");
        }

        public int status() {
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
