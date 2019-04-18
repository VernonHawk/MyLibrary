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
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.LibraryItem;
import com.ukma.mylibrary.entities.SciPubOrder;
import com.ukma.mylibrary.entities.ScientificPublication;
import com.ukma.mylibrary.managers.AuthManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LibraryAdapter extends ArrayAdapter<LibraryItem> {
    private Context mContext;
    private List<LibraryItem> mItemList;
    private APIResponse.Listener<SciPubOrder> mOnOrderSuccess;
    private APIResponse.ErrorListener mOnOrderError;

    public LibraryAdapter(@NonNull Context context, @NonNull List<LibraryItem> list) {
        super(context, 0, list);

        mContext  = context;
        mItemList = list;
    }

    public LibraryAdapter(
        @NonNull final Context context,
        @NonNull final List<LibraryItem> list,
        @NonNull final APIResponse.Listener<SciPubOrder> onOrderSuccess,
        @NonNull final APIResponse.ErrorListener onOrderError
    ) {
        this(context, list);
        mOnOrderSuccess = onOrderSuccess;
        mOnOrderError   = onOrderError;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_library, parent, false);

        // We can't just use listItem because we need it final for the inner class
        final View listItem = view;

        final LibraryItem currentItem = mItemList.get(position);

        TextView name = listItem.findViewById(R.id.item_reserved_sci_pub_name);
        name.setText(currentItem.getName());

        setInfoText(currentItem, listItem);
        setOrderButtonStyle(currentItem, listItem);

        AppCompatImageView itemType = listItem.findViewById(R.id.library_item_icon);
        if (currentItem.getScType() == ScientificPublication.SCType.Book) {
            itemType.setImageResource(R.drawable.ic_bookmark_black_24dp);
            TooltipCompat.setTooltipText(itemType, mContext.getString(R.string.book_tooltip));
        } else {
            itemType.setImageResource(R.drawable.ic_collections_bookmark_black_24dp);
            TooltipCompat.setTooltipText(itemType, mContext.getString(R.string.collection_tooltip));
        }

        final Button takeOrderBtn = listItem.findViewById(R.id.library_item_take_order_btn);
        takeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View __) {
                clickHandler(currentItem, listItem);
            }
        });

        return listItem;
    }

    private void setInfoText(final LibraryItem item, final View listItem) {
        final TextView totalCopies = listItem.findViewById(R.id.library_item_copies);
        final TextView bookState   = listItem.findViewById(R.id.library_item_state);

        totalCopies.setText(String.valueOf(item.getTotalCopies()));
        bookState.setText(item.getState().name().toLowerCase());
    }

    private void setOrderButtonStyle(final LibraryItem item, final View listItem) {
        final Button takeOrderBtn = listItem.findViewById(R.id.library_item_take_order_btn);

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

    private void clickHandler(final LibraryItem item, final View listItem) {
        try {
            JSONObject order = new JSONObject();
            order.put("user_id", AuthManager.CURRENT_USER.getId());
            order.put("scientific_publication_id", item.getId());

            API.call(Route.CreateOrder, SciPubOrder.class)
                .body("order", order)
                .then(new APIResponse.Listener<SciPubOrder>() {
                    @Override
                    public void onResponse(final SciPubOrder response) {
                        item.setScientificPublication(response.getScientificPublication());
                        setInfoText(item, listItem);
                        setOrderButtonStyle(item, listItem);

                        if (mOnOrderSuccess != null) {
                            mOnOrderSuccess.onResponse(response);
                        }
                    }
                })
                .catchError(mOnOrderError)
                .executeWithContext(mContext);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}