package com.ukma.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public
class SignInActivity extends AppCompatActivity {

    @Override protected
    void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // use passed role
    }

    public
    void onToSignUp(final View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public
    void onToLanding(final View view) {
        finish();
    }
}
