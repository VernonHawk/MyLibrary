package com.ukma.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.ukma.mylibrary.api.APIResponse;
import com.ukma.mylibrary.entities.Role;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.managers.AuthManager;
import com.ukma.mylibrary.tools.InputValidator;
import com.ukma.mylibrary.tools.PhoneNumberHelper;
import com.ukma.mylibrary.tools.ToastHelper;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private EditText mUserNameEditText;
    private EditText mUserSurnameEditText;
    private EditText mUserPhoneEditText;
    private EditText mUserPasswordEditText;
    private EditText mUserPasswordConfirmationEditText;

    private static HashMap<InputValidator.Input, TextInputLayout> mInputToLayout = null;

    private enum Input implements InputValidator.Input {
        Name, Surname, PhoneNumber, Password, PasswordConfirmation;

        @Override public String canonicalName() {
            // splits CamelCase into words (Camel Case)
            return TextUtils.join(
                " ", name().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")
            );
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserNameEditText    = findViewById(R.id.reg_input_name);
        mUserSurnameEditText = findViewById(R.id.reg_input_surname);
        PhoneNumberHelper.addFormatting(mUserPhoneEditText = findViewById(R.id.reg_input_phone));
        mUserPasswordEditText = findViewById(R.id.reg_input_password);
        mUserPasswordConfirmationEditText = findViewById(R.id.reg_input_password_repeat);

        mInputToLayout = new HashMap<InputValidator.Input, TextInputLayout>() {{
            put(Input.Name,                 (TextInputLayout) findViewById(R.id.reg_input_name_layout));
            put(Input.Surname,              (TextInputLayout) findViewById(R.id.reg_input_surname_layout));
            put(Input.PhoneNumber,          (TextInputLayout) findViewById(R.id.reg_input_phone_layout));
            put(Input.Password,             (TextInputLayout) findViewById(R.id.reg_input_password_layout));
            put(Input.PasswordConfirmation, (TextInputLayout) findViewById(R.id.reg_input_password_repeat_layout));
        }};
    }

    public void onToSignIn(final View view) {
        finish();
    }

    public void onSignUp(final View view) {
        final Button signUpBtn = findViewById(R.id.sign_up_btn);
        signUpBtn.setEnabled(false);

        final String name                 = mUserNameEditText.getText().toString().trim();
        final String surname              = mUserSurnameEditText.getText().toString().trim();
        final String phoneNumber          = PhoneNumberHelper.normalize(mUserPhoneEditText.getText());
        final String password             = mUserPasswordEditText.getText().toString();
        final String passwordConfirmation = mUserPasswordConfirmationEditText.getText().toString();

        if (!isInputValid(name, surname, phoneNumber, password, passwordConfirmation)) {
            signUpBtn.setEnabled(true);
            return;
        }

        final User user = new User(name, surname, phoneNumber, Role.Reader, password);

        AuthManager.getManager(this).signUp(user,
        new APIResponse.Listener<User>() {
            @Override
            public void onResponse(final User __) {
                startActivity(new Intent(SignUpActivity.this, ReaderActivity.class));
                signUpBtn.setEnabled(true);
            }
        }, new APIResponse.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.e(SignUpActivity.class.getSimpleName(), error.getMessage(), error);
                signUpBtn.setEnabled(true);

                APIResponse.handleError(error, new APIResponse.ErrorIdentifiedListener() {
                    @Override
                    public void onErrorIdentified(final APIResponse.Error error, final int msgId) {
                        if (error == null || error.status() != HttpURLConnection.HTTP_BAD_REQUEST) {
                            ToastHelper.show(SignUpActivity.this, msgId);
                            return;
                        }

                        if (error.detail().has("phone_num")) {
                            ToastHelper.show(SignUpActivity.this, String.format(
                                getString(R.string.already_exists_message), "User", "Phone number")
                            );
                        }
                    }
                });
            }
        });
    }

    private boolean isInputValid(
        final String name, final String surname, final String phoneNumber,
        final String password, final String passwordConfirmation
    ) {
        final Map<InputValidator.Input, String> textInputs =
            new HashMap<InputValidator.Input, String>() {{
                put(Input.Name, name);
                put(Input.Surname, surname);
            }};

        return InputValidator.areInputsValid(
            mInputToLayout,
            textInputs,
            new Pair<InputValidator.Input, String>(Input.PhoneNumber, phoneNumber),
            new Pair<InputValidator.Input, String>(Input.Password, password),
            new Pair<InputValidator.Input, String>(Input.PasswordConfirmation, passwordConfirmation),
            new InputValidator.InvalidInputListener() {
                @Override public void processError(
                    final InputValidator.Input input, final int errStringId
                ) {
                    Objects.requireNonNull(mInputToLayout.get(input)).setError(
                        String.format(getString(errStringId), input.canonicalName())
                    );
                }
            }
        );
    }
}
