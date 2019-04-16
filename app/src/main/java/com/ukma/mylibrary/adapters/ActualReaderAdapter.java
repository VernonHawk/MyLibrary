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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActualReaderAdapter extends ArrayAdapter<ActualReaderItem> {
    private Context mContext;
    private List<ActualReaderItem> itemList;

    public ActualReaderAdapter(@NonNull Context context, ArrayList<ActualReaderItem> list) {
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

        ActualReaderItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.item_reserved_sci_pub_name);
        name.setText(currentItem.getName());

        TextView issueDate = listItem.findViewById(R.id.textView_issueDate);
        issueDate.setText(sdf.format(currentItem.getIssueDate()));

        TextView returnDate = listItem.findViewById(R.id.textView_returnDate);
        returnDate.setText(sdf.format(currentItem.getReturnDate()));

        return listItem;
    }
}
