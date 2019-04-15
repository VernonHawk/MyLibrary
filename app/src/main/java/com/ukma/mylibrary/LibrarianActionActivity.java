package com.ukma.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ukma.mylibrary.adapters.LibrarianReturnAdapter;
import com.ukma.mylibrary.adapters.LibrarianWithdrawalAdapter;
import com.ukma.mylibrary.components.AbstractItem;
import com.ukma.mylibrary.components.LibrarianReturnItem;
import com.ukma.mylibrary.components.LibrarianWithdrawalItem;
import com.ukma.mylibrary.entities.ScientificPublication;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LibrarianActionActivity extends ToolbarLibrarianActivity {
    public enum LibrarianAction {
        RETURN,
        WITHDRAW
    }

    public static LibrarianAction librarianAction;
    static final private int NUM_ITEMS_PAGE = 4;
    public int TOTAL_LIST_ITEMS = 10;
    private ListView listView;
    private TextView title;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<AbstractItem> data;
    private int pageCount;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_action);

        listView = findViewById(R.id.librarian_action_list);
        btnPrev = findViewById(R.id.prev);
        btnNext = findViewById(R.id.next);
        title = findViewById(R.id.title);

        data = new ArrayList<>();

        //this block is for checking the number of pages
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        // The ArrayList data contains all the list items
        for (int i = 0; i < TOTAL_LIST_ITEMS; i++) {
            ScientificPublication sp = new ScientificPublication();
            sp.setName("Name " + (i + 1));
            sp.setIsbn("ISBN" + i);
            sp.setId(i);
            data.add(
                    librarianAction == LibrarianAction.RETURN ?
                            new LibrarianReturnItem(sp, new Date(), new Date())
                            : new LibrarianWithdrawalItem(sp, new Date())
            );
        }
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
        ArrayList sort = new ArrayList<AbstractItem>();
        title.setText(String.format(Locale.getDefault(), getString(R.string.pagination), currentPage + 1, pageCount));

        int start = currentPage * NUM_ITEMS_PAGE;
        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;
            sort.add(data.get(i));
        }
        listView.setAdapter(
                librarianAction == LibrarianAction.RETURN ?
                        new LibrarianReturnAdapter(this, sort)
                        : new LibrarianWithdrawalAdapter(this, sort)
        );
    }
}