package com.ukma.mylibrary.api;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

public class APIRoutes {
    private static Map<Route, RouteData> routes = new HashMap<>();

    static {
        routes.put(Route.SignUp, new RouteData(Request.Method.POST, "/register"));
        routes.put(Route.SignIn, new RouteData(Request.Method.POST, "/login"));
    }

    public static String getRoutePath(Route route) {
        return routes.get(route).getPath();
    }

    public static int getMethodType(Route route) {
        return routes.get(route).getMethod();
    }

    private static class RouteData {
        private int method;
        private String path;

        public RouteData(int method, String path) {
            this.method = method;
            this.path = path;
        }

        public int getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }
    }
}