package com.ukma.mylibrary.entities.factory;

import com.ukma.mylibrary.entities.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface IEntityJSONFactory {
    public JSONObject getEntityJSON(Entity entity);
    public JSONArray getEntityJSONArray(List<Entity> entities);
}
