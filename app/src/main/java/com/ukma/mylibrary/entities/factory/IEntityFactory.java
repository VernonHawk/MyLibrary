package com.ukma.mylibrary.entities.factory;

import com.ukma.mylibrary.entities.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface IEntityFactory {
    Entity getEntity(JSONObject jsonObject, Class c);

    Entity getEntity(String jsonString, Class c);

    List<Entity> getEntityList(JSONArray jsonArray, Class c);

    List<Entity> getEntityList(String jsonArray, Class c);
}
