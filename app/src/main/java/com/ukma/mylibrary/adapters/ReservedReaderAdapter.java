package com.ukma.mylibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.ukma.mylibrary.R;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.ReservedReaderItem;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReservedReaderAdapter extends ArrayAdapter<ReservedReaderItem> {
    private Context mContext;
    private List<ReservedReaderItem> mItemList;
    private Response.Listener<JSONObject> mOnCancelSuccess;
    private APIResponse.ErrorListener mOnCancelError;

    public ReservedReaderAdapter(
        @NonNull Context context,
        final ArrayList<ReservedReaderItem> list
    ) {
        super(context, 0, list);
        mContext = context;
        mItemList = list;
    }

    public ReservedReaderAdapter(
        @NonNull Context context,
        final ArrayList<ReservedReaderItem> list,
        final Response.Listener<JSONObject> onCancelSuccess,
        final APIResponse.ErrorListener onCancelError
    ) {
        super(context, 0, list);
        mContext = context;
        mItemList = list;
        mOnCancelSuccess = onCancelSuccess;
        mOnCancelError = onCancelError;
    }

    @NonNull
    @Override
    public View getView(
        final int position, final @Nullable View convertView, final @NonNull ViewGroup parent
    ) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_reserved, parent, false);

        final ReservedReaderItem currentItem = mItemList.get(position);

        final TextView name = listItem.findViewById(R.id.item_reserved_sci_pub_name);
        name.setText(currentItem.getName());

        final TextView reservationDate = listItem.findViewById(R.id.item_reserved_order_date);
        reservationDate.setText(sdf.format(currentItem.getOrderDate()));

        final Button cancelBtn = listItem.findViewById(R.id.item_reserved_cancel_order_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                handleCancel(currentItem);
            }
        });

        return listItem;
    }

    private void handleCancel(final ReservedReaderItem item) {
        try {
            API.call(Route.CancelOrder)
               .params("id", String.valueOf(item.getId()))
               .then(mOnCancelSuccess)
               .catchError(mOnCancelError)
               .executeWithContext(mContext);
        } catch (final APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }
}
