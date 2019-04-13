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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.R;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.LibraryItem;
import com.ukma.mylibrary.entities.ScientificPublication;
import com.ukma.mylibrary.managers.AuthManager;

import org.json.JSONException;
import org.json.JSONObject;

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

        final LibraryItem currentItem = itemList.get(position);

        TextView name = listItem.findViewById(R.id.item_reserved_sci_pub_name);
        name.setText(currentItem.getName());

        final TextView totalCopies = listItem.findViewById(R.id.library_item_copies);
        final TextView bookState = listItem.findViewById(R.id.library_item_state);
        final Button takeOrderBtn = listItem.findViewById(R.id.library_item_take_order_btn);

        setInfoText(currentItem, totalCopies, bookState);
        setButtonStyle(currentItem, takeOrderBtn);

        AppCompatImageView itemType = listItem.findViewById(R.id.library_item_icon);
        if (currentItem.getScType() == ScientificPublication.SCType.Book) {
            itemType.setImageResource(R.drawable.ic_bookmark_black_24dp);
            TooltipCompat.setTooltipText(itemType, mContext.getString(R.string.book_tooltip));
        } else {
            itemType.setImageResource(R.drawable.ic_collections_bookmark_black_24dp);
            TooltipCompat.setTooltipText(itemType, mContext.getString(R.string.collection_tooltip));
        }

        takeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clickHandler(currentItem, totalCopies, bookState, takeOrderBtn);
            }
        });

        return listItem;
    }

    private void setInfoText(LibraryItem item, TextView totalCopies, TextView bookState) {
        totalCopies.setText(String.valueOf(item.getTotalCopies()));
        bookState.setText(item.getState().name().toLowerCase());
    }

    private void setButtonStyle(LibraryItem item, Button takeOrderBtn) {
        switch (item.getState()) {
            case FREE:
                takeOrderBtn.setText(R.string.btn_take);
                takeOrderBtn.setBackgroundColor(ResourcesCompat.getColor(
                    getContext().getResources(),
                    R.color.colorSuccess,
                    null)
                );
                break;
            case RESERVED:
                takeOrderBtn.setText(R.string.btn_order);
                takeOrderBtn.setBackgroundColor(ResourcesCompat.getColor(
                    getContext().getResources(),
                    R.color.colorPrimaryDark,
                    null)
                );
                break;
        }
    }

    public void clickHandler(final LibraryItem item, final TextView totalCopies, final TextView bookState, final Button takeOrderBtn) {
        try {
            JSONObject order = new JSONObject();
            order.put("user_id", AuthManager.CURRENT_USER.getId());
            order.put("scientific_publication_id", item.getId());
            API.call(Route.CreateOrder)
                .body("order", order)
                .then(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    // TODO change totalCopies Text, booksState and button if needed
                    System.out.println("RESPONSE: " + response.toString());
                    }
                })
                .catchError(new APIResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO add something
                    }
                })
                .executeWithContext(mContext);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}