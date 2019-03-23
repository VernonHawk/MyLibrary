package com.ukma.mylibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.TooltipCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        if (currentItem.getItemType() == ItemUtils.ItemType.BOOK) {
            itemType.setImageResource(R.drawable.ic_bookmark_black_24dp);
            TooltipCompat.setTooltipText(itemType, mContext.getString(R.string.book_tooltip));
        } else {
            itemType.setImageResource(R.drawable.ic_collections_bookmark_black_24dp);
            TooltipCompat.setTooltipText(itemType, mContext.getString(R.string.collection_tooltip));
        }

        Button button = listItem.findViewById(R.id.button);
        switch (currentItem.getState()) {
            case FREE:
                button.setText(R.string.btn_take);
                button.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),
                        R.color.colorSuccess,
                        null));
                break;
            case RESERVED:
                button.setText(R.string.btn_order);
                button.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),
                        R.color.colorPrimaryDark,
                        null));
                break;
        }

        return listItem;
    }
}