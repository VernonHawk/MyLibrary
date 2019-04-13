package com.ukma.mylibrary;

import android.view.Menu;

public class ToolbarLibrarianActivity extends ToolbarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.librarian_menu, menu);
        return true;
    }
}