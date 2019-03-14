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
import com.ukma.mylibrary.components.ActualReaderItem;

import java.util.ArrayList;
import java.util.List;

public class ActualReaderAdapter extends ArrayAdapter<ActualReaderItem> {
    private Context mContext;
    private List<ActualReaderItem> itemList;

    public ActualReaderAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ActualReaderItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_actual, parent, false);

        ActualReaderItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.textView_name);
        name.setText(currentItem.getItemName());

        TextView issueDate = listItem.findViewById(R.id.textView_issueDate);
        issueDate.setText(currentItem.getItemIssueDate().toString());

        TextView returnDate = listItem.findViewById(R.id.textView_returnDate);
        returnDate.setText(currentItem.getItemReturnDate().toString());

        return listItem;
    }
}
