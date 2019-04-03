package com.ukma.mylibrary;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.adapters.LibraryAdapter;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.AbstractReaderItem;
import com.ukma.mylibrary.components.LibraryItem;
import com.ukma.mylibrary.entities.ScientificPublication;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.ToastHelper;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Locale;

public class LibraryActivity extends ToolbarActivity {
    private static final int NUM_ITEMS_PAGE = 4;
    private ListView listView;
    private TextView title;
    private SearchView searchItem;
    private Button btnSearch;
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
        searchItem = findViewById(R.id.search_item);
        btnSearch = findViewById(R.id.sci_pub_search_btn);
        btnPrev = findViewById(R.id.prev);
        btnNext = findViewById(R.id.next);
        title = findViewById(R.id.title);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findData(searchItem.getQuery().toString());
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
        findData("");
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

    private void findData(String search) {
        btnSearch.setEnabled(false);
        try {
            final Context context = this;
            API.call(Route.SearchSciPub, ScientificPublication.class)
                .query("search", search)
                .thenWithArray(new APIResponse.Listener<ArrayList<ScientificPublication>>() {
                    @Override
                    public void onResponse(ArrayList<ScientificPublication> scientificPublications) {
                        btnSearch.setEnabled(true);
                        setData(scientificPublications);
                    }
                })
                .catchError(new APIResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnSearch.setEnabled(true);
                        Log.e(LibraryActivity.class.getSimpleName(), error.getMessage(), error);

                        final Pair<APIResponse.Error, Integer> errWithMsg = APIResponse.handleError(error);
                        final APIResponse.Error err = errWithMsg.first;
                        if (err == null) {
                            ToastHelper.show(context, R.string.some_error_message);
                        } else if (err.status() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            ToastHelper.show(context, errWithMsg.second);
                            signOut();
                            return;
                        } else {
                            ToastHelper.show(context, R.string.some_error_message);
                        }
                    }
                })
                .executeWithContext(context);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    private void setData(ArrayList<ScientificPublication> scientificPublications) {
        int TOTAL_LIST_ITEMS = scientificPublications.size();
        data = new ArrayList<>();
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        for (ScientificPublication scientificPublication: scientificPublications) {
            data.add(new LibraryItem(scientificPublication));
        }
        currentPage = 0;
        loadList(currentPage);
        CheckEnable();
    }
}
