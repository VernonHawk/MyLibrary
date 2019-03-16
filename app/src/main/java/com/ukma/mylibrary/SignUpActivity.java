package com.ukma.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.Tools.PhoneNumberHelper;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.entities.Role;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.managers.AuthManager;

import org.json.JSONObject;

public
class SignUpActivity extends AppCompatActivity {

    private EditText mUserNameEditText;
    private EditText mUserSurnameEditText;
    private EditText mUserPhoneEditText;
    private EditText mUserPasswordEditText;
    private EditText mUserPasswordConfirmationEditText;

    @Override protected
    void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserNameEditText = findViewById(R.id.reg_input_name);
        mUserSurnameEditText = findViewById(R.id.reg_input_surname);
        PhoneNumberHelper.addFormatting(mUserPhoneEditText = findViewById(R.id.reg_input_phone));
        mUserPasswordEditText = findViewById(R.id.reg_input_password);
        mUserPasswordConfirmationEditText = findViewById(R.id.reg_input_password_repeat);
    }

    public
    void onToSignIn(final View view) {
        finish();
    }

    public
    void onSignUp(final View view) {
        final String userPassword = mUserPasswordEditText.getText().toString();
        final String userPasswordConfirmation = mUserPasswordConfirmationEditText.getText().toString();

        if (userPassword.compareTo(userPasswordConfirmation) != 0) {
            // TODO ...
        }

        final String userName = mUserNameEditText.getText().toString();
        final String userSurname = mUserSurnameEditText.getText().toString();
        final String userPhoneNumber = PhoneNumberHelper.normalize(mUserPhoneEditText.getText());

        AuthManager authManager = AuthManager.getManager(this);
        User user = new User(userName, userSurname, userPhoneNumber, Role.Reader, userPassword);

        authManager.signUp(user, new APIResponse.Listener<User>() {
            @Override
            public void onResponse(User receivedUser) {
                System.out.println(receivedUser);
            }
        }, new APIResponse.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });


    }
}
