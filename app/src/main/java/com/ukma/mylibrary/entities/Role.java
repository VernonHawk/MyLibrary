package com.ukma.mylibrary.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    Reader, Librarian, Archivist;

    private static Map<Role, RoleObject> rolesMap = new HashMap<>(3);

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RoleObject implements Entity {
        @JsonProperty("id")
        private long id;
        @JsonProperty("name")
        private String name;

        public RoleObject() {
        }

        public RoleObject(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static {
        rolesMap.put(Role.Reader, new RoleObject(1, Role.Reader.name().toUpperCase()));
        rolesMap.put(Role.Librarian, new RoleObject(2, Role.Librarian.name().toUpperCase()));
        rolesMap.put(Role.Archivist, new RoleObject(3, Role.Archivist.name().toUpperCase()));
    }

    @JsonCreator
    public static Role forValue(RoleObject roleObject) {
        for (Map.Entry<Role, RoleObject> entry : rolesMap.entrySet()) {
            if (entry.getValue().id == roleObject.id) {
                return entry.getKey();
            }
        }
        return null;
    }

    @JsonValue
    public RoleObject toValue() {
        return rolesMap.get(this);
    }
}
