package com.ukma.mylibrary.entities.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukma.mylibrary.entities.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class EntityFactory implements IEntityFactory {
    public EntityFactory() {
    }

    @Override
    public Entity getEntity(JSONObject jsonObject, Class c) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (Entity) objectMapper.readValue(jsonObject.toString(), c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Entity getEntity(String jsonString, Class c) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (Entity) objectMapper.readValue(jsonString, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Entity> getEntityList(JSONArray jsonArray, Class c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonArray.toString(), mapper.getTypeFactory().constructCollectionType(List.class, c));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Entity> getEntityList(String jsonStringArray, Class c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStringArray, mapper.getTypeFactory().constructCollectionType(List.class, c));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
