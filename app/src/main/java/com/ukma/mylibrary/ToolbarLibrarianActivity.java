package com.ukma.mylibrary;

import android.view.Menu;

import com.ukma.mylibrary.managers.AuthManager;

public class ToolbarLibrarianActivity extends ToolbarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.librarian_menu, menu);
        menu.findItem(R.id.name_librarian).setTitle(AuthManager.CURRENT_USER.getName());

        return true;
    }
}