package com.ukma.mylibrary.api;

import com.ukma.mylibrary.entities.Entity;

public class API<E extends Entity> {
    private final static String BASE_PATH = "https://mylibraryapplication.herokuapp.com";

    private static String normalizeRoutePath(String route) {
        if (route.charAt(0) == '/') {
            return BASE_PATH + route;
        }
        return BASE_PATH + "/" + route;
    }

    public APIRequest call(Route route) {
        return new APIRequest(
            normalizeRoutePath(APIRoutes.getRoutePath(route)),
            APIRoutes.getMethodType(route)
        );
    }

    public APIRequest call(Route route, Class c) {
        return new APIRequest<E>(
            normalizeRoutePath(APIRoutes.getRoutePath(route)),
            APIRoutes.getMethodType(route),
            c
        );
    }
}
