package com.ukma.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import java.util.HashMap;

public
class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.ukma.mylybrary.extra.MESSAGE";

    final static
    HashMap<Integer, Role> radio_button_to_role = new HashMap<Integer, Role>() {{
        put(R.id.reader, Role.Reader);
        put(R.id.librarian, Role.Librarian);
        put(R.id.archivist, Role.Archivist);
    }};

    @Override protected
    void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public
    void onContinueClicked(final View view) {
        final int id = ((RadioGroup) findViewById(R.id.roles)).getCheckedRadioButtonId();

        final Intent intent = new Intent(MainActivity.this, SignInActivity.class);

        intent.putExtra(EXTRA_MESSAGE, radio_button_to_role.get(id));

        startActivity(intent);
    }
}
