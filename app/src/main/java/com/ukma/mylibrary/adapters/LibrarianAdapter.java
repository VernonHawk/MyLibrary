package com.ukma.mylibrary.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ukma.mylibrary.LibrarianActionActivity;
import com.ukma.mylibrary.R;
import com.ukma.mylibrary.components.LibrarianItem;
import com.ukma.mylibrary.entities.User;

import java.util.ArrayList;
import java.util.List;

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_readers, parent, false);

        final LibrarianItem currentItem = itemList.get(position);

        final TextView name = listItem.findViewById(R.id.list_readers_reader_name);
        name.setText(String.format(mContext.getString(R.string.reader_header),
                                   currentItem.getUserName(), currentItem.getUserSurname()));

        final Button toWithdrawBtn = listItem.findViewById(R.id.list_readers_withdraw_btn);
        toWithdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View __) {
                clickHandler(currentItem.getUser(), LibrarianActionActivity.LibrarianAction.WITHDRAW);
            }
        });

        final Button toReturnBtn = listItem.findViewById(R.id.list_readers_return_btn);
        toReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View __) {
                clickHandler(currentItem.getUser(), LibrarianActionActivity.LibrarianAction.RETURN);
            }
        });

        return listItem;
    }

    private void clickHandler(final User user, final LibrarianActionActivity.LibrarianAction action) {
        final Bundle extras = new Bundle();
        extras.putSerializable("User", user);
        extras.putSerializable("Action", action);

        final Intent intent = new Intent(mContext, LibrarianActionActivity.class);
        intent.putExtras(extras);

        mContext.startActivity(intent);
    }
}
