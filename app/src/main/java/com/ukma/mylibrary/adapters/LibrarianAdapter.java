package com.ukma.mylibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ukma.mylibrary.R;
import com.ukma.mylibrary.components.ActualReaderItem;
import com.ukma.mylibrary.components.LibrarianItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LibrarianAdapter extends ArrayAdapter<LibrarianItem> {
    private Context mContext;
    private List<LibrarianItem> itemList;

    public LibrarianAdapter(@NonNull Context context, ArrayList<LibrarianItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_readers, parent, false);

        LibrarianItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.textUserID);
        name.setText(String.valueOf(currentItem.getUserId()));

        return listItem;
    }
}