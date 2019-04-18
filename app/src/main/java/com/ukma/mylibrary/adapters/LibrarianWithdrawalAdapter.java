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
import com.ukma.mylibrary.components.LibrarianWithdrawalItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LibrarianWithdrawalAdapter extends ArrayAdapter<LibrarianWithdrawalItem> {
    private Context mContext;
    private List<LibrarianWithdrawalItem> itemList;

    public LibrarianWithdrawalAdapter(@NonNull Context context, ArrayList<LibrarianWithdrawalItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_librarian_withdrawal, parent, false);
        }

        final LibrarianWithdrawalItem currentItem = itemList.get(position);

        final TextView name = listItem.findViewById(R.id.publicationName);
        name.setText(currentItem.getPublicationName());

        final TextView isbn = listItem.findViewById(R.id.isbn);
        isbn.setText(currentItem.getIsbn());

        final TextView copyId = listItem.findViewById(R.id.copy_id);
        copyId.setText(String.valueOf(currentItem.getCopyId()));

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final TextView orderDate = listItem.findViewById(R.id.order_date);
        orderDate.setText(sdf.format(currentItem.getOrderDate()));

        return listItem;
    }
}
