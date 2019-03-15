package com.ukma.mylibrary.entities.factory;

import com.ukma.mylibrary.entities.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface IEntityFactory {
    public Entity getEntity(JSONObject jsonObject, Class c);
    public Entity getEntity(String jsonString, Class c);
    public List<Entity> getEntityList(JSONArray jsonArray, Class c);
    public List<Entity> getEntityList(String jsonArray, Class c);
}
