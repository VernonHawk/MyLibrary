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
import com.ukma.mylibrary.tools.ItemUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_librarian_return, parent, false);
        }

        final LibrarianReturnItem currentItem = itemList.get(position);

        final TextView name = listItem.findViewById(R.id.publicationName);
        name.setText(currentItem.getPublicationName());

        final TextView isbn = listItem.findViewById(R.id.isbn);
        isbn.setText(currentItem.getIsbn());

        final TextView copyId = listItem.findViewById(R.id.copy_id);
        copyId.setText(String.valueOf(currentItem.getCopyId()));

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final TextView issueDate = listItem.findViewById(R.id.issueDate);
        issueDate.setText(sdf.format(currentItem.getIssueDate()));

        final TextView expectedDate = listItem.findViewById(R.id.expectedDate);
        expectedDate.setText(sdf.format(currentItem.getExpectedDate()));

        if (currentItem.getExpectedDate().before(Calendar.getInstance().getTime())) {
            ((TextView) listItem.findViewById(R.id.expectedDateTitle))
                .setTextColor(ItemUtils.ResourceColorToColor(getContext(), R.color.colorAccent));

            expectedDate.setTextColor(ItemUtils.ResourceColorToColor(getContext(), R.color.colorAccent));
        }

        return listItem;
    }
}
