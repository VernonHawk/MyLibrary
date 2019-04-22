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
import com.ukma.mylibrary.tools.ItemUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_actual, parent, false);
        }

        final ActualReaderItem currentItem = itemList.get(position);

        final TextView name = listItem.findViewById(R.id.item_reserved_sci_pub_name);
        name.setText(currentItem.getName());

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        final TextView issueDate = listItem.findViewById(R.id.textView_issueDate);
        issueDate.setText(sdf.format(currentItem.getIssueDate()));

        final TextView expectedReturnDate = listItem.findViewById(R.id.list_item_actual_exp_return_date);
        expectedReturnDate.setText(sdf.format(currentItem.getExpectedReturnDate()));

        if (currentItem.getExpectedReturnDate().before(Calendar.getInstance().getTime())) {
            ((TextView) listItem.findViewById(R.id.list_item_actual_exp_return_date_title))
                .setTextColor(ItemUtils.ResourceColorToColor(getContext(), R.color.colorAccent));

            expectedReturnDate.setTextColor(ItemUtils.ResourceColorToColor(getContext(), R.color.colorAccent));
        }

        return listItem;
    }
}
