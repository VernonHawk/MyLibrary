package com.ukma.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ukma.mylibrary.adapters.LibrarianAdapter;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.AbstractItem;
import com.ukma.mylibrary.components.LibrarianItem;

import java.util.ArrayList;
import java.util.Locale;

public class LibrarianMainActivity extends ToolbarLibrarianActivity {
    private static final int NUM_ITEMS_PAGE = 3;
    private ListView listView;
    private TextView title;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<AbstractItem> data = new ArrayList<>();
    private int pageCount;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_userlist);

        listView = findViewById(R.id.list);
        btnPrev = findViewById(R.id.prev);
        btnNext = findViewById(R.id.next);
        title = findViewById(R.id.title);

        data = new ArrayList<>();

        //this block is for checking the number of pages
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        // The ArrayList data contains all the list items

        for (long i = 0; i < TOTAL_LIST_ITEMS; i++)
            data.add(new LibrarianItem(i + 1));
        currentPage = 0;
        loadList(currentPage);
        CheckEnable();

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

    public void OnReturnClick(final View view) {
        LibrarianActionActivity.librarianAction = LibrarianActionActivity.LibrarianAction.RETURN;
        startActivity(new Intent(this, LibrarianActionActivity.class));
    }

    public void OnWithdrawClick(final View view) {
        LibrarianActionActivity.librarianAction = LibrarianActionActivity.LibrarianAction.WITHDRAW;
        startActivity(new Intent(this, LibrarianActionActivity.class));
    }
}