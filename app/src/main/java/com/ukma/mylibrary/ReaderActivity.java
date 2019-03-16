package com.ukma.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.ukma.mylibrary.adapters.ActualReaderAdapter;
import com.ukma.mylibrary.components.ActualReaderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReaderActivity extends AppCompatActivity {
    public int TOTAL_LIST_ITEMS = 10;
    static final private int NUM_ITEMS_PAGE = 4;
    private ListView listView;
    private TextView title;
    private Switch swActual;
    private Switch swReserved;
    private Button btnPrev;
    private Button btnNext;
    private ArrayList<ActualReaderItem> data;
    private int pageCount;
    private int currentPage = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reader_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_orders);

        listView = findViewById(R.id.list);
        btnPrev = findViewById(R.id.prev);
        btnNext = findViewById(R.id.next);
        title = findViewById(R.id.title);
        swActual = findViewById(R.id.switch_actual);
        swReserved = findViewById(R.id.switch_reserved);

        swActual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        swReserved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        btnPrev.setEnabled(false);
        data = new ArrayList<>();

        //this block is for checking the number of pages
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        // The ArrayList data contains all the list items
        for (int i = 0; i < TOTAL_LIST_ITEMS; i++)
            data.add(new ActualReaderItem("Book " + (i + 1), new Date(), new Date()));
        loadList(0);

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
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable() {
        if (currentPage + 1 == pageCount)
            btnNext.setEnabled(false);
        else if (currentPage == 0)
            btnPrev.setEnabled(false);
        else {
            btnPrev.setEnabled(true);
            btnNext.setEnabled(true);
        }
    }

    /**
     * Method for loading data in listview
     *
     * @param pageNum
     */
    private void loadList(int pageNum) {
        ArrayList<ActualReaderItem> sort = new ArrayList<>();
        title.setText(String.format(Locale.getDefault(), "Page %d of %d", pageNum + 1, pageCount));

        int start = pageNum * NUM_ITEMS_PAGE;
        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;
            sort.add(data.get(i));
        }
        ActualReaderAdapter rAdapter = new ActualReaderAdapter(this, sort);
        listView.setAdapter(rAdapter);
    }
}