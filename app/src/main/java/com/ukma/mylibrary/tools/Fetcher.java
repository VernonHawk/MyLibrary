package com.ukma.mylibrary.tools;

import android.content.Context;

import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequest;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.entities.CopyIssue;
import com.ukma.mylibrary.entities.SciPubOrder;

import java.util.ArrayList;

public class Fetcher {
    public static void fetchOrdersForUser(
        final Context context,
        final long userId,
        final SciPubOrder.Status status,
        final Boolean isReady,
        final APIResponse.Listener<ArrayList<SciPubOrder>> onSuccess,
        final APIResponse.ErrorListener onError
    ) {
        try {
            APIRequest req = API.call(Route.GetOrdersForUser, SciPubOrder.class)
                .params("user_id", String.valueOf(userId));

            if (status != null) {
                req = req.query("status", status.name());
            }

            if (isReady != null) {
                req = req.query("is_ready", String.valueOf(isReady));
            }

            req.thenWithArray(onSuccess)
               .catchError(onError)
               .executeWithContext(context);
        } catch (final APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public static void fetchCopyIssuesForUser(
        final Context context,
        final long userId,
        final APIResponse.Listener<ArrayList<CopyIssue>> onSuccess,
        final APIResponse.ErrorListener onError
    ) {
        try {
            API.call(Route.GetCopyIssuesForUser, CopyIssue.class)
               .params("user_id", String.valueOf(userId))
               .thenWithArray(onSuccess)
               .catchError(onError)
               .executeWithContext(context);
        } catch (final APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public static void withdrawCopy(
        final Context context,
        final long orderId,
        final APIResponse.Listener<SciPubOrder> onSuccess,
        final APIResponse.ErrorListener onError
    ) {
        try {
            API.call(Route.WithdrawCopy, SciPubOrder.class)
               .params("id", String.valueOf(orderId))
               .then(onSuccess)
               .catchError(onError)
               .executeWithContext(context);
        } catch (final APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public static void returnCopy(
        final Context context,
        final long issueId,
        final APIResponse.Listener<CopyIssue> onSuccess,
        final APIResponse.ErrorListener onError
    ) {
        try {
            API.call(Route.ReturnCopy, CopyIssue.class)
               .params("id", String.valueOf(issueId))
               .then(onSuccess)
               .catchError(onError)
               .executeWithContext(context);
        } catch (final APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }
}
