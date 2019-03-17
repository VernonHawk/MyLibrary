package com.ukma.mylibrary.entities.factory;

import com.ukma.mylibrary.entities.Entity;
import com.ukma.mylibrary.entities.Role;
import com.ukma.mylibrary.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class EntityJSONFactoryTest {

    @Test
    public void getEntityJSON() {
        User user = new User("USER_NAME", "USER_SURNAME", "USER_PHONE", Role.Reader);
        EntityJSONFactory entityJSONFactory = new EntityJSONFactory();
        JSONObject userJsonObject = entityJSONFactory.getEntityJSON(user);
        try {
            assertEquals(user.getName(), userJsonObject.get("name"));
            assertEquals(user.getSurname(), userJsonObject.get("surname"));
            assertEquals(user.getPhoneNum(), userJsonObject.get("phone_num"));
            assertEquals("{\"name\":\"READER\",\"id\":1}", userJsonObject.get("role").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEntityJSONArray() {
        List<Entity> users = new ArrayList<>();
        users.add(new User("USER_NAME_1", "USER_SURNAME_1", "USER_PHONE_1", Role.Reader));
        users.add(new User("USER_NAME_2", "USER_SURNAME_2", "USER_PHONE_2", Role.Librarian));
        users.add(new User("USER_NAME_3", "USER_SURNAME_3", "USER_PHONE_3", Role.Archivist));

        EntityJSONFactory entityJSONFactory = new EntityJSONFactory();
        JSONArray usersJsonArray = entityJSONFactory.getEntityJSONArray(users);
        try {
            assertEquals("{\"role\":{\"name\":\"READER\",\"id\":1},\"surname\":\"USER_SURNAME_1\",\"name\":\"USER_NAME_1\",\"phone_num\":\"USER_PHONE_1\"}", usersJsonArray.get(0).toString());
            assertEquals("{\"role\":{\"name\":\"LIBRARIAN\",\"id\":2},\"surname\":\"USER_SURNAME_2\",\"name\":\"USER_NAME_2\",\"phone_num\":\"USER_PHONE_2\"}", usersJsonArray.get(1).toString());
            assertEquals("{\"role\":{\"name\":\"ARCHIVIST\",\"id\":3},\"surname\":\"USER_SURNAME_3\",\"name\":\"USER_NAME_3\",\"phone_num\":\"USER_PHONE_3\"}", usersJsonArray.get(2).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}