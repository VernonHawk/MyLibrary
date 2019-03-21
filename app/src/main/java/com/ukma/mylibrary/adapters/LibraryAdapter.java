package com.ukma.mylibrary.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ukma.mylibrary.R;
import com.ukma.mylibrary.components.LibraryItem;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends ArrayAdapter<LibraryItem> {
    private Context mContext;
    private List<LibraryItem> itemList;

    public LibraryAdapter(@NonNull Context context, ArrayList<LibraryItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_library, parent, false);

        LibraryItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.textView_name);
        name.setText(currentItem.getItemName());

        TextView totalCopies = listItem.findViewById(R.id.textView_copies);
        totalCopies.setText(String.valueOf(currentItem.getTotalCopies()));

        TextView bookState = listItem.findViewById(R.id.textView_state);
        bookState.setText(currentItem.getState().name().toLowerCase());

        AppCompatImageView itemType = listItem.findViewById(R.id.item_icon);
        itemType.setImageResource(currentItem.getItemType() == ItemUtils.ItemType.BOOK ?
                R.drawable.ic_bookmark_black_24dp : R.drawable.ic_collections_bookmark_black_24dp);

        Button button = listItem.findViewById(R.id.button);
        switch (currentItem.getState()) {
            case FREE:
                button.setText("Take");
                button.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),
                        R.color.colorSuccess,
                        null));
                break;
            case RESERVED:
                button.setText("Order");
                button.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),
                        R.color.colorPrimaryDark,
                        null));
                break;
        }

        return listItem;
    }

}
