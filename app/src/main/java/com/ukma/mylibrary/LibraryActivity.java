package com.ukma.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ukma.mylibrary.adapters.ItemUtils;
import com.ukma.mylibrary.adapters.LibraryAdapter;
import com.ukma.mylibrary.components.AbstractReaderItem;
import com.ukma.mylibrary.components.LibraryItem;

import java.util.ArrayList;
import java.util.Locale;

public class LibraryActivity extends ToolbarActivity {
    static final private int NUM_ITEMS_PAGE = 4;
    public int TOTAL_LIST_ITEMS = 10;
    private ListView listView;
    private TextView title;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<AbstractReaderItem> data;
    private int pageCount;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_library);

        listView = findViewById(R.id.list);
        btnPrev = findViewById(R.id.prev);
        btnNext = findViewById(R.id.next);
        title = findViewById(R.id.title);

        data = new ArrayList<>();

        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        data.clear();
        for (int i = 0; i < TOTAL_LIST_ITEMS; i++)
            data.add(new LibraryItem(
                    "Book " + (i + 1),
                    i,
                    i % 2 == 0 ? ItemUtils.BookState.FREE : ItemUtils.BookState.RESERVED,
                    i % 2 == 0 ? ItemUtils.ItemType.BOOK : ItemUtils.ItemType.COLLECTION)
            );
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

//        listView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //todo on click action
//            }
//        });
    }

    /**
     Method for enabling and disabling pagination Buttons
     */
    private void CheckEnable() {
        btnPrev.setEnabled(currentPage > 0);
        btnNext.setEnabled(currentPage + 1 < pageCount);
    }

    private void loadList(int currentPage) {
        ArrayList sort = new ArrayList<AbstractReaderItem>();
        title.setText(String.format(Locale.getDefault(), "Page %d of %d", currentPage + 1, pageCount));

        int start = currentPage * NUM_ITEMS_PAGE;
        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;
            sort.add(data.get(i));
        }
        listView.setAdapter(new LibraryAdapter(this, sort));
    }
}
