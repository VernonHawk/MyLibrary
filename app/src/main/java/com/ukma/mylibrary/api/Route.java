package com.ukma.mylibrary.api;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

public enum Route {
    SignUp,
    SignIn,
    TestRoute;

    private static Map<Route, RouteData> routes = new HashMap<>();

    static {
        routes.put(Route.SignUp, new RouteData(Request.Method.POST, "/register"));
        routes.put(Route.SignIn, new RouteData(Request.Method.POST, "/login"));
        routes.put(Route.TestRoute, new RouteData(Request.Method.POST, "/test/{id}/{other}"));
    }

    public String getRoutePath() {
        return this.routes.get(this).getPath();
    }

    public int getMethodType() {
        return this.routes.get(this).getMethod();
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

