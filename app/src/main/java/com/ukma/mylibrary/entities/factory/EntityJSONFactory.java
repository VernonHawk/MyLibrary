package com.ukma.mylibrary.entities.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukma.mylibrary.entities.Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EntityJSONFactory implements IEntityJSONFactory {
    @Override
    public JSONObject getEntityJSON(Entity entity) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new JSONObject(mapper.writeValueAsString(entity));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray getEntityJSONArray(List<Entity> entities) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new JSONArray(mapper.writeValueAsString(entities));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
