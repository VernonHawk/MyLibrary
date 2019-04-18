package com.ukma.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.adapters.LibrarianReturnAdapter;
import com.ukma.mylibrary.adapters.LibrarianWithdrawalAdapter;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.components.AbstractItem;
import com.ukma.mylibrary.components.LibrarianReturnItem;
import com.ukma.mylibrary.components.LibrarianWithdrawalItem;
import com.ukma.mylibrary.entities.CopyIssue;
import com.ukma.mylibrary.entities.SciPubOrder;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.tools.Fetcher;

import java.util.ArrayList;

public class LibrarianActionActivity extends ToolbarLibrarianActivity {
    public enum LibrarianAction {
        RETURN,
        WITHDRAW
    }

    private static final int NUM_ITEMS_PAGE = 2;

    private ListView listView;
    private TextView paginationTitle;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<AbstractItem> data = new ArrayList<>();
    private int pageCount;
    private int currentPage = 0;

    private LibrarianAction mAction = LibrarianAction.RETURN;
    private User mCurrentReader;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_action);

        final Bundle extras = getIntent().getExtras();
        assert extras != null;

        mCurrentReader = (User) extras.getSerializable("User");
        assert mCurrentReader != null;

        mAction = (LibrarianAction) extras.getSerializable("Action");
        assert mAction != null;


        ((TextView) findViewById(R.id.librarian_action_reader_name)).setText(
            String.format(getString(R.string.reader_header),
                          mCurrentReader.getName(), mCurrentReader.getSurname())
        );

        listView = findViewById(R.id.librarian_action_list);
        btnPrev  = findViewById(R.id.prev);
        btnNext  = findViewById(R.id.next);
        paginationTitle = findViewById(R.id.title);

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage++;
                loadList(currentPage);
                CheckEnable();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage--;
                loadList(currentPage);
                CheckEnable();
            }
        });

        if (IsReturnAction()) {
            fetchReturnItems();
        } else {
            fetchWithdrawalItems();
        }
    }

    /**
     * Method for enabling and disabling pagination Buttons
     */
    private void CheckEnable() {
        btnPrev.setEnabled(currentPage > 0);
        btnNext.setEnabled(currentPage + 1 < pageCount);
    }

    /**
     * Method for loading data in listview
     *
     * @param currentPage page to load data for
     */
    @SuppressWarnings("unchecked")
    private void loadList(int currentPage) {
        paginationTitle.setText(String.format(getString(R.string.pagination), currentPage + 1, pageCount));

        final ArrayList sort = new ArrayList<AbstractItem>();
        int start = currentPage * NUM_ITEMS_PAGE;

        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;

            sort.add(data.get(i));
        }

        listView.setAdapter( IsReturnAction() ? new LibrarianReturnAdapter(this, sort)
                                              : new LibrarianWithdrawalAdapter(this, sort)
        );
    }

    private void setWithdrawalData(final ArrayList<SciPubOrder> orders) {
        data.clear();
        for (final SciPubOrder order : orders) {
            data.add(new LibrarianWithdrawalItem(order));
        }

        reloadPagination(orders.size());
    }

    private void setReturnData(final ArrayList<CopyIssue> issues) {
        data.clear();
        for (final CopyIssue issue : issues) {
            data.add(new LibrarianReturnItem(issue));
        }

        reloadPagination(issues.size());
    }

    private void fetchWithdrawalItems() {
        Fetcher.fetchOrdersForUser(this,
           mCurrentReader.getId(), SciPubOrder.Status.Pending, true,
           new APIResponse.Listener<ArrayList<SciPubOrder>>() {
               @Override
               public void onResponse(final ArrayList<SciPubOrder> orders) {
                   setWithdrawalData(orders);
               }
           },
           new APIResponse.ErrorListener() {
               @Override
               public void onErrorResponse(final VolleyError error) {
                   handleError(error, LibrarianActionActivity.this);
               }
            }
        );
    }

    private void fetchReturnItems() {
        Fetcher.fetchCopyIssuesForUser(this, mCurrentReader.getId(),
            new APIResponse.Listener<ArrayList<CopyIssue>>() {
                @Override
                public void onResponse(final ArrayList<CopyIssue> issues) {
                    setReturnData(issues);
                }
            }, new APIResponse.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    handleError(error, LibrarianActionActivity.this);
                }
            }
        );
    }

    private void reloadPagination(final int elemCount) {
        pageCount = elemCount / NUM_ITEMS_PAGE;

        if (elemCount % NUM_ITEMS_PAGE > 0) {
            ++pageCount;
        }

        currentPage = 0;
        loadList(currentPage);
        CheckEnable();
    }

    private boolean IsReturnAction() {
        return mAction == LibrarianAction.RETURN;
    }
}