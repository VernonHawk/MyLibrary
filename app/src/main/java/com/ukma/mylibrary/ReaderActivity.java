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
    public int NUM_ITEMS_PAGE = 4;
    private ListView listView;
    private TextView title;
    private Switch sw_actual;
    private Switch sw_reserved;
    private Button btn_prev;
    private Button btn_next;
    private ArrayList<ActualReaderItem> data;
    private int pageCount;
    private int increment = 0;

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
        btn_prev = findViewById(R.id.prev);
        btn_next = findViewById(R.id.next);
        title = findViewById(R.id.title);
        sw_actual = findViewById(R.id.switch_actual);
        sw_reserved = findViewById(R.id.switch_reserved);

        sw_actual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        sw_reserved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        btn_prev.setEnabled(false);
        data = new ArrayList<>();

        //this block is for checking the number of pages
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        // The ArrayList data contains all the list items
        for (int i = 0; i < TOTAL_LIST_ITEMS; i++)
            data.add(new ActualReaderItem("Book " + (i + 1), new Date(), new Date()));
        loadList(0);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment++;
                loadList(increment);
                CheckEnable();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment--;
                loadList(increment);
                CheckEnable();
            }
        });
    }

    /**
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable() {
        if (increment + 1 == pageCount)
            btn_next.setEnabled(false);
        else if (increment == 0)
            btn_prev.setEnabled(false);
        else {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }

    /**
     * Method for loading data in listview
     *
     * @param number
     */
    private void loadList(int number) {
        ArrayList<ActualReaderItem> sort = new ArrayList<>();
        title.setText(String.format(Locale.ENGLISH, "Page %d of %d", number + 1, pageCount));

        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < data.size())
                sort.add(data.get(i));
            else
                break;
        }
        ActualReaderAdapter rAdapter = new ActualReaderAdapter(this, sort);
        listView.setAdapter(rAdapter);
    }
}