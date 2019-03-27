package com.ukma.mylibrary;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public
class SignInActivity extends AppCompatActivity {
    private static final HashMap mRoleToActivity = new HashMap<Role, Class>() {{
        put(Role.Reader, ReaderActivity.class);
        put(Role.Librarian, null);
        put(Role.Archivist, null);
    }};

    private Role mUserRole;

    private EditText mPhoneEditText;
    private EditText mUserPasswordEditText;

    private static HashMap<InputValidator.Input, TextInputLayout> mInputToLayout = null;

    private enum Input implements InputValidator.Input {
        PhoneNumber, Password;

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
        setContentView(R.layout.activity_sign_in);

        mUserPasswordEditText = findViewById(R.id.input_password);
        mUserRole = (Role) getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        if (mUserRole != Role.Reader)
            findViewById(R.id.to_sign_up_layout).setVisibility(View.GONE);

        final boolean roleStartsWithVowel =
                "aeiou".indexOf(Character.toLowerCase(mUserRole.name().charAt(0))) != -1;

        final TextView notUserRole = findViewById(R.id.label_not_user_role);
        notUserRole.setPaintFlags(notUserRole.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        formatLabelWithRole(
            notUserRole.getId(),
            roleStartsWithVowel ? R.string.not_an_user_role : R.string.not_a_user_role
        );
        formatLabelWithRole(R.id.hello_label, R.string.hello_user_role);

        PhoneNumberHelper.addFormatting(mPhoneEditText = findViewById(R.id.input_phone));

        mInputToLayout = new HashMap<InputValidator.Input, TextInputLayout>() {{
            put(Input.PhoneNumber, (TextInputLayout) findViewById(R.id.input_phone_layout));
            put(Input.Password, (TextInputLayout) findViewById(R.id.input_password_layout));
        }};
    }

    public void onToSignUp(final View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public void onToLanding(final View view) {
        finish();
    }

    private void formatLabelWithRole(final int viewId, final int stringId) {
        ((TextView) findViewById(viewId)).setText(
                String.format(getString(stringId), mUserRole.name())
        );
    }

    public void onSignIn(final View view) {
        final Button signInBtn = findViewById(R.id.sign_in_btn);
        signInBtn.setEnabled(false);

        final String phoneNumber = PhoneNumberHelper.normalize(mPhoneEditText.getText());
        final String password = mUserPasswordEditText.getText().toString();

        if (!isInputValid(phoneNumber, password)) {
            signInBtn.setEnabled(true);
            return;
        }

        AuthManager.getManager(this).signIn(phoneNumber, password,
         new APIResponse.Listener<User>() {
            @Override
            public void onResponse(final User __) {
                startActivity(new Intent(
                    SignInActivity.this, (Class) mRoleToActivity.get(mUserRole)
                ));
                signInBtn.setEnabled(true);
            }
        }, new APIResponse.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError reqError) {
                Log.e(SignInActivity.class.getSimpleName(), reqError.getMessage(), reqError);

                final Pair<APIResponse.Error, Integer> errWithMsg = APIResponse.handleError(reqError);
                final APIResponse.Error err = errWithMsg.first;

                signInBtn.setEnabled(true);

                if (err == null || err.status() != HttpURLConnection.HTTP_UNAUTHORIZED) {
                    ToastHelper.show(SignInActivity.this, errWithMsg.second);
                    return;
                }

                if (err.detail().has("phone_num")) {
                    ToastHelper.show(SignInActivity.this, String.format(
                        getString(R.string.entity_not_found_message), Input.PhoneNumber.canonicalName())
                    );
                } else if (err.detail().has("password")) {
                    ToastHelper.show(SignInActivity.this, R.string.password_invalid_message);
                }
            }
        });
    }

    private boolean isInputValid(final String phoneNumber, final String password) {
        final Map<InputValidator.Input, String> textInputs =
            new HashMap<InputValidator.Input, String>() {{
                put(Input.PhoneNumber, phoneNumber);
                put(Input.Password, password);
            }};

        return InputValidator.areInputsValid(
            mInputToLayout,
            textInputs,
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
