package com.ukma.mylibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.adapters.ActualReaderAdapter;
import com.ukma.mylibrary.adapters.ItemUtils;
import com.ukma.mylibrary.adapters.ReservedReaderAdapter;
import com.ukma.mylibrary.api.API;
import com.ukma.mylibrary.api.APIRequestNoListenerSpecifiedException;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.api.Route;
import com.ukma.mylibrary.components.AbstractItem;
import com.ukma.mylibrary.components.ActualReaderItem;
import com.ukma.mylibrary.components.ReservedReaderItem;
import com.ukma.mylibrary.entities.CopyIssue;
import com.ukma.mylibrary.entities.SciPubOrder;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.ToastHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ReaderActivity extends ToolbarReaderActivity {
    private static final int NUM_ITEMS_PAGE = 3;

    private ListView listView;
    private TextView title;
    private Button btnPrev;
    private Button btnNext;

    private ArrayList<AbstractItem> data = new ArrayList<>();
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

        // The ArrayList data contains all the list items
        RadioGroup radioGroup = findViewById(R.id.toggle);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setActiveItem(checkedId);
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

        setActiveItem(R.id.rb_actual);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        signOut();
    }

    private void setActiveItem(int checkedId) {
        switch (checkedId) {
            case R.id.rb_actual:
                orderType = ItemUtils.OrderType.ACTUAL;
                fetchActualItems();
                break;
            case R.id.rb_reserved:
                orderType = ItemUtils.OrderType.RESERVED;
                fetchReservedItems();
                break;
            default:
                Log.e(ReaderActivity.class.getSimpleName(), "Unknown checked id");
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
        title.setText(String.format(Locale.getDefault(), getString(R.string.pagination), currentPage + 1, pageCount));

        final ArrayList sort = new ArrayList<AbstractItem>();

        int start = currentPage * NUM_ITEMS_PAGE;
        for (int i = start; i < start + NUM_ITEMS_PAGE; i++) {
            if (i >= data.size())
                break;

            sort.add(data.get(i));
        }

        if (orderType == ItemUtils.OrderType.ACTUAL) {
            listView.setAdapter(new ActualReaderAdapter(this, sort));
        } else {
            listView.setAdapter(new ReservedReaderAdapter(this, sort,
            new Response.Listener<JSONObject>() {
                @Override public void onResponse(final JSONObject response) {
                    ToastHelper.show(ReaderActivity.this, R.string.order_cancel_success);
                    fetchReservedItems();
                }
            }, new APIResponse.ErrorListener() {
                @Override public void onErrorResponse(final VolleyError error) {
                    handleError(error, ReaderActivity.this);
                }
            })
            );
        }
    }

    private void setOrdersData(final ArrayList<SciPubOrder> orders) {
        if (orderType == ItemUtils.OrderType.ACTUAL) {
            Log.e(ReaderActivity.class.getSimpleName(), "Data and order type mismatch");
            return;
        }

        data.clear();
        for (SciPubOrder order: orders) {
            data.add(new ReservedReaderItem(order));
        }

        reloadPagination(orders.size());
    }

    private void setIssuesData(final ArrayList<CopyIssue> issues) {
        if (orderType == ItemUtils.OrderType.RESERVED) {
            Log.e(ReaderActivity.class.getSimpleName(), "Data and order type mismatch");
            return;
        }

        data.clear();
        for (CopyIssue issue: issues) {
            data.add(new ActualReaderItem(issue));
        }

        reloadPagination(issues.size());
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

    private void fetchReservedItems() {
        try {
            API.call(Route.GetOrdersForUser, SciPubOrder.class)
               .params("user_id", String.valueOf(AuthManager.CURRENT_USER.getId()))
               .query("status", SciPubOrder.Status.Pending.name())
               .thenWithArray(new APIResponse.Listener<ArrayList<SciPubOrder>>() {
                   @Override
                   public void onResponse(ArrayList<SciPubOrder> orders) {
                       setOrdersData(orders);
                   }
               })
               .catchError(new APIResponse.ErrorListener() {
                   @Override
                   public void onErrorResponse(final VolleyError error) {
                       handleError(error, ReaderActivity.this);
                   }
               })
               .executeWithContext(this);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    private void fetchActualItems() {
        try {
            API.call(Route.GetCopyIssuesForUser, CopyIssue.class)
               .params("user_id", String.valueOf(AuthManager.CURRENT_USER.getId()))
               .thenWithArray(new APIResponse.Listener<ArrayList<CopyIssue>>() {
                   @Override
                   public void onResponse(ArrayList<CopyIssue> issues) {
                       setIssuesData(issues);
                   }
               })
               .catchError(new APIResponse.ErrorListener() {
                   @Override
                   public void onErrorResponse(final VolleyError error) {
                       handleError(error, ReaderActivity.this);
                   }
               })
               .executeWithContext(this);
        } catch (APIRequestNoListenerSpecifiedException e) {
            e.printStackTrace();
        }
    }
}