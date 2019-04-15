package com.ukma.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ukma.mylibrary.adapters.ActualReaderAdapter;
import com.ukma.mylibrary.adapters.ItemUtils;
import com.ukma.mylibrary.adapters.ReservedReaderAdapter;
import com.ukma.mylibrary.components.AbstractReaderItem;
import com.ukma.mylibrary.components.ActualReaderItem;
import com.ukma.mylibrary.components.ReservedReaderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReaderActivity extends ToolbarReaderActivity {
    static final private int NUM_ITEMS_PAGE = 4;
    public int TOTAL_LIST_ITEMS = 10;
    private ListView listView;
    private TextView title;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<AbstractReaderItem> data;
    private int pageCount;
    private int currentPage = 0;
    private ItemUtils.OrderType orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_orders);

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
        RadioGroup radioGroup = findViewById(R.id.toggle);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setActiveItem(checkedId);
            }
        });
        setActiveItem(R.id.rb_actual);

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

    @Override protected void onDestroy() {
        super.onDestroy();
        signOut();
    }

    private void setActiveItem(int checkedId) {
        data.clear();
        switch (checkedId) {
            case R.id.rb_actual:
                for (int i = 0; i < TOTAL_LIST_ITEMS; i++)
                    data.add(new ActualReaderItem("Book " + (i + 1), new Date(), new Date()));
                orderType = ItemUtils.OrderType.ACTUAL;
                break;
            case R.id.rb_reserved:
                for (int i = 0; i < TOTAL_LIST_ITEMS; i++)
                    data.add(new ReservedReaderItem("Book " + (i + 1), new Date()));
                orderType = ItemUtils.OrderType.RESERVED;
                break;
        }
        currentPage = 0;
        loadList(currentPage);
        CheckEnable();
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
        ArrayList sort = new ArrayList<AbstractReaderItem>();
        title.setText(String.format(Locale.getDefault(), "Page %d of %d", currentPage + 1, pageCount));

        int start = currentPage * NUM_ITEMS_PAGE;
        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;
            sort.add(data.get(i));
        }
        listView.setAdapter(orderType == ItemUtils.OrderType.ACTUAL ?
                new ActualReaderAdapter(this, sort) : new ReservedReaderAdapter(this, sort));
    }
}