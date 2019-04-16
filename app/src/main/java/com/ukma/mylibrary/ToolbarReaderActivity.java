package com.ukma.mylibrary;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.ukma.mylibrary.managers.AuthManager;

public class ToolbarReaderActivity extends ToolbarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reader_menu, menu);
        menu.findItem(R.id.name_reader).setTitle(AuthManager.CURRENT_USER.getName());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_orders:
                startActivity(new Intent(this, ReaderActivity.class));
                return true;
            case R.id.action_library:
                startActivity(new Intent(this, LibraryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}