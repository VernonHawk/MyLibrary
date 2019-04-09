package com.ukma.mylibrary;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class ToolbarReaderActivity extends ToolbarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reader_menu, menu);
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
            case android.R.id.home:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}