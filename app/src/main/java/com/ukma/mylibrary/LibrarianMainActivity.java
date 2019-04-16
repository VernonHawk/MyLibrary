package com.ukma.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.adapters.LibrarianAdapter;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.AbstractItem;
import com.ukma.mylibrary.components.LibrarianItem;
import com.ukma.mylibrary.entities.User;

import java.util.ArrayList;

public class LibrarianMainActivity extends ToolbarLibrarianActivity {
    private static final int NUM_ITEMS_PAGE = 3;
    private ListView listView;
    private TextView title;
    private SearchView searchItem;
    private Button btnSearch;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<AbstractItem> data = new ArrayList<>();
    private int pageCount;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_userlist);

        listView = findViewById(R.id.librarian_userlist_list);
        searchItem = findViewById(R.id.librarian_userlist_search_view);
        btnSearch = findViewById(R.id.librarian_userlist_search_btn);
        btnPrev = findViewById(R.id.prev);
        btnNext = findViewById(R.id.next);
        title = findViewById(R.id.title);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                fetchUsers(searchItem.getQuery().toString());
            }
        });

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

        fetchUsers("");
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
        title.setText(String.format(getString(R.string.pagination), currentPage + 1, pageCount));

        ArrayList sort = new ArrayList<AbstractItem>();
        int start = currentPage * NUM_ITEMS_PAGE;

        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;

            sort.add(data.get(i));
        }

        listView.setAdapter(new LibrarianAdapter(this, sort));
    }

    private void fetchUsers(final String filterPhoneNum) {
        btnSearch.setEnabled(false);

        try {
            API.call(Route.GetReaders, User.class)
               .thenWithArray(new APIResponse.Listener<ArrayList<User>>() {
                   @Override
                   public void onResponse(final ArrayList<User> users) {
                       btnSearch.setEnabled(true);
                       setData(filterData(users, filterPhoneNum));
                   }
               })
               .catchError(new APIResponse.ErrorListener() {
                   @Override
                   public void onErrorResponse(final VolleyError error) {
                        btnSearch.setEnabled(true);
                        handleError(error, LibrarianMainActivity.this);
                   }
               })
               .executeWithContext(this);
        } catch (final APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<User> filterData(final ArrayList<User> users, final String filterPhoneNum) {
        final ArrayList<User> res = new ArrayList<>();

        for (final User user : users) {
            if (user.getPhoneNum().contains(filterPhoneNum)) {
                res.add(user);
            }
        }

        return res;
    }

    private void setData(final ArrayList<User> users) {
        data.clear();
        for (final User user: users) {
            data.add(new LibrarianItem(user));
        }

        pageCount = data.size() / NUM_ITEMS_PAGE;
        if (data.size() % NUM_ITEMS_PAGE > 0) {
            ++pageCount;
        }

        currentPage = 0;
        loadList(currentPage);
        CheckEnable();
    }

    public void OnReturnClick(final View view) {
        LibrarianActionActivity.librarianAction = LibrarianActionActivity.LibrarianAction.RETURN;
        startActivity(new Intent(this, LibrarianActionActivity.class));
    }

    public void OnWithdrawClick(final View view) {
        LibrarianActionActivity.librarianAction = LibrarianActionActivity.LibrarianAction.WITHDRAW;
        startActivity(new Intent(this, LibrarianActionActivity.class));
    }
}