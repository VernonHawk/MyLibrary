package com.ukma.mylibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ukma.mylibrary.R;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.components.LibrarianWithdrawalItem;
import com.ukma.mylibrary.entities.SciPubOrder;
import com.ukma.mylibrary.tools.Fetcher;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LibrarianWithdrawalAdapter extends ArrayAdapter<LibrarianWithdrawalItem> {
    private Context mContext;
    private List<LibrarianWithdrawalItem> itemList;
    private APIResponse.Listener<SciPubOrder> mOnWithdrawSuccess;
    private APIResponse.ErrorListener mOnWithdrawError;

    public LibrarianWithdrawalAdapter(@NonNull Context context, List<LibrarianWithdrawalItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    public LibrarianWithdrawalAdapter(
        @NonNull final Context context,
        @NonNull final List<LibrarianWithdrawalItem> list,
        @NonNull final APIResponse.Listener<SciPubOrder> onWithdrawSuccess,
        @NonNull final APIResponse.ErrorListener onWithdrawError
    ) {
        this(context, list);
        mOnWithdrawSuccess = onWithdrawSuccess;
        mOnWithdrawError   = onWithdrawError;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_librarian_withdrawal, parent, false);
        }

        final LibrarianWithdrawalItem currentItem = itemList.get(position);

        final TextView name = listItem.findViewById(R.id.list_librarian_withdrawal_publicationName);
        name.setText(currentItem.getPublicationName());

        final TextView isbn = listItem.findViewById(R.id.isbn);
        isbn.setText(currentItem.getIsbn());

        final TextView copyId = listItem.findViewById(R.id.list_librarian_withdrawal_copy_id);
        copyId.setText(String.valueOf(currentItem.getCopyId()));

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final TextView orderDate = listItem.findViewById(R.id.list_librarian_withdrawal_order_date);
        orderDate.setText(sdf.format(currentItem.getOrderDate()));

        final Button withdrawApproval = listItem.findViewById(
            R.id.list_librarian_withdrawal_withdrawal_approve
        );
        withdrawApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View __) {
                Fetcher.withdrawCopy(
                    mContext, currentItem.getOrderId(), mOnWithdrawSuccess, mOnWithdrawError
                );
            }
        });

        return listItem;
    }
}
