package com.ukma.mylibrary.entities.factory;

import com.ukma.mylibrary.entities.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface IEntityJSONFactory {
    JSONObject getEntityJSON(Entity entity);

    JSONArray getEntityJSONArray(List<Entity> entities);
}
