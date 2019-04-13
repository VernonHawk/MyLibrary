package com.ukma.mylibrary.entities.factory;

import com.ukma.mylibrary.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class EntityFactoryTest {

    @Test
    public void getEntity() {
        EntityFactory entityFactory = new EntityFactory();
        final String userJSON = "{\"name\":\"USER_NAME\",\"surname\":\"USER_SURNAME\",\"phone_num\":\"+380673672134\", \"role\":{\"id\":1,\"name\":\"READER\"}}";
        User user = (User) entityFactory.getEntity(userJSON, User.class);
        assertEquals("USER_NAME", user.getName());
        assertEquals("USER_SURNAME", user.getSurname());
        assertEquals("+380673672134", user.getPhoneNum());
        assertEquals("READER", user.getRole().name().toUpperCase());
    }

    @Test
    public void getEntity1() {
        EntityFactory entityFactory = new EntityFactory();
        final String userJSON = "{\"name\":\"USER_NAME\",\"surname\":\"USER_SURNAME\",\"phone_num\":\"+380673672134\", \"role\":{\"id\":1,\"name\":\"READER\"}}";
        try {
            JSONObject userJsonObject = new JSONObject(userJSON);
            User user = (User) entityFactory.getEntity(userJsonObject, User.class);
            assertEquals("USER_NAME", user.getName());
            assertEquals("USER_SURNAME", user.getSurname());
            assertEquals("+380673672134", user.getPhoneNum());
            assertEquals("READER", user.getRole().name().toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEntityList() {
        EntityFactory entityFactory = new EntityFactory();
        final String user_1_JSON = "{\"name\":\"USER_NAME_1\",\"surname\":\"USER_SURNAME_1\",\"phone_num\":\"+380673672134_1\", \"role\":{\"id\":1,\"name\":\"READER\"}}";
        final String user_2_JSON = "{\"name\":\"USER_NAME_2\",\"surname\":\"USER_SURNAME_2\",\"phone_num\":\"+380673672134_2\", \"role\":{\"id\":2,\"name\":\"LIBRARIAN\"}}";
        final String user_3_JSON = "{\"name\":\"USER_NAME_3\",\"surname\":\"USER_SURNAME_3\",\"phone_num\":\"+380673672134_3\", \"role\":{\"id\":3,\"name\":\"ARCHIVIST\"}}";
        final String usersJSON = "["+user_1_JSON+","+user_2_JSON+","+user_3_JSON+"]";
        ArrayList<User> users = (ArrayList) entityFactory.getEntityList(usersJSON, User.class);
        assertEquals("USER_NAME_1", users.get(0).getName());
        assertEquals("USER_SURNAME_1", users.get(0).getSurname());
        assertEquals("+380673672134_1", users.get(0).getPhoneNum());
        assertEquals("READER", users.get(0).getRole().name().toUpperCase());

        assertEquals("USER_NAME_2", users.get(1).getName());
        assertEquals("USER_SURNAME_2", users.get(1).getSurname());
        assertEquals("+380673672134_2", users.get(1).getPhoneNum());
        assertEquals("LIBRARIAN", users.get(1).getRole().name().toUpperCase());

        assertEquals("USER_NAME_3", users.get(2).getName());
        assertEquals("USER_SURNAME_3", users.get(2).getSurname());
        assertEquals("+380673672134_3", users.get(2).getPhoneNum());
        assertEquals("ARCHIVIST", users.get(2).getRole().name().toUpperCase());
    }

    @Test
    public void getEntityList1() {
        EntityFactory entityFactory = new EntityFactory();
        final String user_1_JSON = "{\"name\":\"USER_NAME_1\",\"surname\":\"USER_SURNAME_1\",\"phone_num\":\"+380673672134_1\", \"role\":{\"id\":1,\"name\":\"READER\"}}";
        final String user_2_JSON = "{\"name\":\"USER_NAME_2\",\"surname\":\"USER_SURNAME_2\",\"phone_num\":\"+380673672134_2\", \"role\":{\"id\":2,\"name\":\"LIBRARIAN\"}}";
        final String user_3_JSON = "{\"name\":\"USER_NAME_3\",\"surname\":\"USER_SURNAME_3\",\"phone_num\":\"+380673672134_3\", \"role\":{\"id\":3,\"name\":\"ARCHIVIST\"}}";
        final String usersJSON = "["+user_1_JSON+","+user_2_JSON+","+user_3_JSON+"]";
        try {
            final JSONArray usersJSONArray = new JSONArray(usersJSON);
            ArrayList<User> users = (ArrayList) entityFactory.getEntityList(usersJSONArray, User.class);
            assertEquals("USER_NAME_1", users.get(0).getName());
            assertEquals("USER_SURNAME_1", users.get(0).getSurname());
            assertEquals("+380673672134_1", users.get(0).getPhoneNum());
            assertEquals("READER", users.get(0).getRole().name().toUpperCase());

            assertEquals("USER_NAME_2", users.get(1).getName());
            assertEquals("USER_SURNAME_2", users.get(1).getSurname());
            assertEquals("+380673672134_2", users.get(1).getPhoneNum());
            assertEquals("LIBRARIAN", users.get(1).getRole().name().toUpperCase());

            assertEquals("USER_NAME_3", users.get(2).getName());
            assertEquals("USER_SURNAME_3", users.get(2).getSurname());
            assertEquals("+380673672134_3", users.get(2).getPhoneNum());
            assertEquals("ARCHIVIST", users.get(2).getRole().name().toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}