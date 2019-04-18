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
import com.ukma.mylibrary.components.LibrarianReturnItem;
import com.ukma.mylibrary.entities.CopyIssue;
import com.ukma.mylibrary.tools.Fetcher;
import com.ukma.mylibrary.tools.ItemUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LibrarianReturnAdapter extends ArrayAdapter<LibrarianReturnItem> {
    private Context mContext;
    private List<LibrarianReturnItem> itemList;
    private APIResponse.Listener<CopyIssue> mOnReturnSuccess;
    private APIResponse.ErrorListener mOnReturnError;

    public LibrarianReturnAdapter(@NonNull Context context, List<LibrarianReturnItem> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    public LibrarianReturnAdapter(
        @NonNull final Context context,
        @NonNull final List<LibrarianReturnItem> list,
        @NonNull final APIResponse.Listener<CopyIssue> onReturnSuccess,
        @NonNull final APIResponse.ErrorListener onReturnError
    ) {
        this(context, list);
        mOnReturnSuccess = onReturnSuccess;
        mOnReturnError = onReturnError;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_librarian_return, parent, false);
        }

        final LibrarianReturnItem currentItem = itemList.get(position);

        final TextView name = listItem.findViewById(R.id.list_librarian_return_publicationName);
        name.setText(currentItem.getPublicationName());

        final TextView isbn = listItem.findViewById(R.id.list_librarian_return_isbn);
        isbn.setText(currentItem.getIsbn());

        final TextView copyId = listItem.findViewById(R.id.list_librarian_return_copy_id);
        copyId.setText(String.valueOf(currentItem.getCopyId()));

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final TextView issueDate = listItem.findViewById(R.id.list_librarian_return_issueDate);
        issueDate.setText(sdf.format(currentItem.getIssueDate()));

        final TextView expectedDate = listItem.findViewById(R.id.list_librarian_return_expectedDate);
        expectedDate.setText(sdf.format(currentItem.getExpectedDate()));

        if (currentItem.getExpectedDate().before(Calendar.getInstance().getTime())) {
            ((TextView) listItem.findViewById(R.id.expectedDateTitle))
                .setTextColor(ItemUtils.ResourceColorToColor(getContext(), R.color.colorAccent));

            expectedDate.setTextColor(ItemUtils.ResourceColorToColor(getContext(), R.color.colorAccent));
        }

        final Button returnApproval = listItem.findViewById(
            R.id.list_librarian_return_return_approve
        );
        returnApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View __) {
                Fetcher.returnCopy(
                    mContext, currentItem.getIssueId(), mOnReturnSuccess, mOnReturnError
                );
            }
        });

        return listItem;
    }
}
