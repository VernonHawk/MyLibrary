package com.ukma.mylibrary.api;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.entities.Entity;
import com.ukma.mylibrary.entities.factory.EntityFactory;
import com.ukma.mylibrary.entities.factory.EntityJSONFactory;
import com.ukma.mylibrary.managers.RequestQueueManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIRequest<T extends Entity> {
    private Class entityClass = null;
    private String path;
    private int method;
    private Map<String, String> routeParams = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private JSONObject requestObject = null;
    private JSONArray requestArray = null;
    private APIResponse.Listener<T> apiResponseObjectListener = null;
    private APIResponse.Listener<List<T>> apiResponseArrayListener = null;
    private APIResponse.ErrorListener apiResponseErrorListener = null;

    private Response.Listener<JSONObject> responseObjectListener = null;
    private Response.Listener<JSONArray> responseArrayListener = null;
    private Response.ErrorListener errorListener;

    public APIRequest(String path, int method) {
        this.path = path;
        this.method = method;
        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponseErrorListener.onErrorResponse(error);
            }
        };
    }

    public APIRequest(String path, int method, Class entityClass) {
        this.path = path;
        this.method = method;
        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponseErrorListener.onErrorResponse(error);
            }
        };
        this.entityClass = entityClass;
    }

    private String generatePath() {
        String generatedPath = this.path;
        generatedPath += "?";
        for (Map.Entry<String, String> entry : routeParams.entrySet()) {
            generatedPath = generatedPath.replaceFirst("\\{" + entry.getKey() + "\\}", entry.getValue());
        }
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            generatedPath += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return generatedPath.substring(0, generatedPath.length() - 1);
    }

    public APIRequest params(Map<String, String> routeParams) {
        this.routeParams.putAll(routeParams);
        return this;
    }

    public APIRequest params(String key, String value) {
        this.routeParams.put(key, value);
        return this;
    }

    public APIRequest body(T entity) {
        this.requestObject = new EntityJSONFactory().getEntityJSON(entity);
        return this;
    }

    public APIRequest body(List<T> entities) {
        this.requestArray = new EntityJSONFactory().getEntityJSONArray((List<Entity>) entities);
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
        this.queryParams.putAll(queryParams);
        return this;
    }

    public APIRequest query(String key, String value) {
        this.queryParams.put(key, value);
        return this;
    }

    public APIRequest then(APIResponse.Listener<T> apiResponseObjectListener) {
        this.apiResponseObjectListener = apiResponseObjectListener;
        return this;
    }

    public APIRequest thenWithArray(APIResponse.Listener<List<T>> apiResponseArrayListener) {
        this.apiResponseArrayListener = apiResponseArrayListener;
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

    public APIRequest catchError(APIResponse.ErrorListener apiResponseErrorListener) {
        this.apiResponseErrorListener = apiResponseErrorListener;
        return this;
    }

    public void executeWithContext(Context context) throws APIRequestNoListenerSpecifiedException {
        final String finalPath = generatePath();
        if (this.responseObjectListener != null) {
            APIJsonObjectRequest apiJsonObjectRequest = new APIJsonObjectRequest(
                this.method,
                finalPath,
                this.requestObject,
                this.responseObjectListener,
                this.errorListener
            );
            RequestQueueManager.getInstance(context).addToRequestQueue(apiJsonObjectRequest);
        } else if (this.responseArrayListener != null) {
            APIJsonArrayRequest apiJsonArrayRequest = new APIJsonArrayRequest(
                this.method,
                finalPath,
                this.requestArray,
                this.responseArrayListener,
                this.errorListener
            );
            RequestQueueManager.getInstance(context).addToRequestQueue(apiJsonArrayRequest);
        } else if (this.apiResponseObjectListener != null) {
            APIJsonObjectRequest apiJsonObjectRequest = new APIJsonObjectRequest(
                this.method,
                finalPath,
                this.requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        T entity = (T) new EntityFactory().getEntity(response, entityClass);
                        apiResponseObjectListener.onResponse(entity);
                    }
                },
                this.errorListener
            );
            RequestQueueManager.getInstance(context).addToRequestQueue(apiJsonObjectRequest);
        } else if (this.apiResponseArrayListener != null) {
            APIJsonArrayRequest apiJsonArrayRequest = new APIJsonArrayRequest(
                this.method,
                finalPath,
                this.requestArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<T> entities = (List<T>) new EntityFactory().getEntityList(response, entityClass);
                        apiResponseArrayListener.onResponse(entities);
                    }
                },
                this.errorListener
            );
            RequestQueueManager.getInstance(context).addToRequestQueue(apiJsonArrayRequest);
        } else {
            throw new APIRequestNoListenerSpecifiedException("No listener specified!!! Use `then` or `thenWithArray` method.");
        }
    }
}