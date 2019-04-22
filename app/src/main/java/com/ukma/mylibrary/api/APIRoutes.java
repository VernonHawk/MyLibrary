package com.ukma.mylibrary.api;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class APIRoutes {
    private static Map<Route, RouteData> routes = new HashMap<>();

    static {
        routes.put(Route.SignUp, new RouteData(Request.Method.POST, "/register"));
        routes.put(Route.SignIn, new RouteData(Request.Method.POST, "/login"));
        routes.put(Route.SignOut, new RouteData(Request.Method.DELETE, "/logout"));
        routes.put(Route.GetCurrentUser, new RouteData(Request.Method.GET, "/users/current"));
        routes.put(Route.SearchSciPub, new RouteData(Request.Method.GET, "/scientific-publications"));
        routes.put(Route.CreateOrder, new RouteData(Request.Method.POST, "/orders"));
        routes.put(Route.GetOrdersForUser, new RouteData(Request.Method.GET, "/orders/{user_id}")); // ?status=:status&is_ready=:is_ready
        routes.put(Route.CancelOrder, new RouteData(Request.Method.PATCH, "/orders/{id}/cancel"));
        routes.put(Route.GetCopyIssuesForUser, new RouteData(Request.Method.GET, "/issues/{user_id}"));
        routes.put(Route.GetReaders, new RouteData(Request.Method.GET, "/users/readers"));
        routes.put(Route.WithdrawCopy, new RouteData(Request.Method.PATCH, "/orders/{id}/withdraw"));
        routes.put(Route.ReturnCopy, new RouteData(Request.Method.PUT, "/issues/{id}/return"));
        routes.put(Route.UpdateFCMToken, new RouteData(Request.Method.PUT, "/users/current/fcm_token"));
    }

    public static String getRoutePath(Route route) {
        return Objects.requireNonNull(routes.get(route)).getPath();
    }

    public static int getMethodType(Route route) {
        return Objects.requireNonNull(routes.get(route)).getMethod();
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
