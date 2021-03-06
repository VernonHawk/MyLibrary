package com.ukma.mylibrary.api;

public class API {
    private final static String BASE_PATH = "https://mylibraryapplication.herokuapp.com";

    private static String normalizeRoutePath(String route) {
        if (route.charAt(0) == '/') {
            return BASE_PATH + route;
        }
        return BASE_PATH + "/" + route;
    }

    public static APIRequest call(Route route) {
        return new APIRequest(
                normalizeRoutePath(APIRoutes.getRoutePath(route)),
                APIRoutes.getMethodType(route)
        );
    }

    public static APIRequest call(Route route, Class c) {
        return new APIRequest(
                normalizeRoutePath(APIRoutes.getRoutePath(route)),
                APIRoutes.getMethodType(route),
                c
        );
    }
}
