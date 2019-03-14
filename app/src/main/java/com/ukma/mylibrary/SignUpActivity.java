package com.ukma.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public
class SignUpActivity extends AppCompatActivity {

    private EditText mPhoneEditText;

    @Override protected
    void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        PhoneNumberHelper.addFormatting(mPhoneEditText = findViewById(R.id.reg_input_phone));
    }

    public
    void onToSignIn(final View view) {
        finish();
    }

    public
    void onSignUp(final View view) {
        final String phoneNumber = PhoneNumberHelper.normalize(mPhoneEditText.getText());

        // ...
    }
}
