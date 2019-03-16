package com.ukma.mylibrary.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ukma.mylibrary.R;
import com.ukma.mylibrary.components.ReservedReaderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReservedReaderAdapter extends ArrayAdapter<ReservedReaderItem> {
    private Context mContext;
    private List<ReservedReaderItem> itemList;

    public ReservedReaderAdapter(@NonNull Context context, ArrayList<ReservedReaderItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_actual, parent, false);

        ReservedReaderItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.textView_name);
        name.setText(currentItem.getItemName());

        TextView reservationDate = listItem.findViewById(R.id.textView_reservationDate);
        reservationDate.setText(sdf.format(currentItem.getItemReservationDate()));

        return listItem;
    }
}
