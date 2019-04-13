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
import com.ukma.mylibrary.components.LibrarianReturnItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LibrarianReturnAdapter extends ArrayAdapter<LibrarianReturnItem> {
    private Context mContext;
    private List<LibrarianReturnItem> itemList;

    public LibrarianReturnAdapter(@NonNull Context context, ArrayList<LibrarianReturnItem> list) {
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
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_librarian_return, parent, false);

        LibrarianReturnItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.publicationName);
        name.setText(currentItem.getPublicationName());

        name = listItem.findViewById(R.id.isbn);
        name.setText(currentItem.getIsbn());

        name = listItem.findViewById(R.id.copy_id);
        name.setText(String.valueOf(currentItem.getCopyId()));

        name = listItem.findViewById(R.id.issueDate);
        name.setText(sdf.format(currentItem.getIssueDate()));

        name = listItem.findViewById(R.id.expectedDate);
        name.setText(sdf.format(currentItem.getExpectedDate()));

        return listItem;
    }
}
