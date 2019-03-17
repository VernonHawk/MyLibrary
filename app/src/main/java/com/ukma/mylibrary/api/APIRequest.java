package com.ukma.mylibrary.api;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.entities.Entity;
import com.ukma.mylibrary.entities.factory.EntityFactory;
import com.ukma.mylibrary.entities.factory.EntityJSONFactory;
import com.ukma.mylibrary.managers.RequestQueueManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIRequest {
    private Class entityClass = null;
    private String path;
    private int method;
    private Map<String, String> routeParams = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, JSONObject> bodyObjectParams = new HashMap<>();
    private Map<String, JSONArray> bodyArrayParams = new HashMap<>();
    private JSONObject requestObject = null;
    private JSONArray requestArray = null;
    private APIResponse.Listener<Entity> apiResponseObjectListener = null;
    private APIResponse.Listener<List<Entity>> apiResponseArrayListener = null;
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
        this(path, method);
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

    private void generateRequestObject() {
        this.requestObject = new JSONObject();
        for (Map.Entry<String, JSONObject> entry : bodyObjectParams.entrySet()) {
            try {
                requestObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, JSONArray> entry : bodyArrayParams.entrySet()) {
            try {
                requestObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public APIRequest params(Map<String, String> routeParams) {
        this.routeParams.putAll(routeParams);
        return this;
    }

    public APIRequest params(String key, String value) {
        this.routeParams.put(key, value);
        return this;
    }

    public APIRequest body(String key, Entity entity) {
        this.bodyObjectParams.put(key, new EntityJSONFactory().getEntityJSON(entity));
        return this;
    }

    public APIRequest body(List<Entity> entities) {
        this.requestArray = new EntityJSONFactory().getEntityJSONArray(entities);
        return this;
    }

    public APIRequest body(String key, List<Entity> entities) {
        this.bodyArrayParams.put(key, new EntityJSONFactory().getEntityJSONArray(entities));
        return this;
    }

    public APIRequest body(String key, JSONObject requestObject) {
        this.bodyObjectParams.put(key, requestObject);
        return this;
    }

    public APIRequest body(JSONArray requestArray) {
        this.requestArray = requestArray;
        return this;
    }

    public APIRequest body(String key, JSONArray requestArray) {
        this.bodyArrayParams.put(key, requestArray);
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

    public <E extends Entity> APIRequest then(APIResponse.Listener<E> apiResponseObjectListener) {
        this.apiResponseObjectListener = (APIResponse.Listener<Entity>) apiResponseObjectListener;
        return this;
    }

    public <E extends Entity, L extends List<E>> APIRequest thenWithArray(APIResponse.Listener<L> apiResponseArrayListener) {
        this.apiResponseArrayListener = (APIResponse.Listener<List<Entity>>) apiResponseArrayListener;
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
            this.generateRequestObject();
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
            this.generateRequestObject();
            APIJsonObjectRequest apiJsonObjectRequest = new APIJsonObjectRequest(
                    this.method,
                    finalPath,
                    this.requestObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Entity entity = new EntityFactory().getEntity(response, entityClass);
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
                            List<Entity> entities = new EntityFactory().getEntityList(response, entityClass);
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