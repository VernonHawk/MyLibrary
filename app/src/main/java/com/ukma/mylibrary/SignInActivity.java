package com.ukma.mylibrary;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ukma.mylibrary.Tools.PhoneNumberHelper;
import com.ukma.mylibrary.entities.Role;
import com.ukma.mylibrary.entities.User;
import com.ukma.mylibrary.entities.factory.EntityFactory;
import com.ukma.mylibrary.managers.AuthManager;

import org.json.JSONObject;

public
class SignInActivity extends AppCompatActivity {

    private Role mUserRole;

    private EditText mPhoneEditText;
    private EditText mUserPasswordEditText;

    @Override protected
    void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUserRole = (Role) getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);
        mUserPasswordEditText = findViewById(R.id.input_password);

        if (mUserRole != Role.Reader)
            findViewById(R.id.to_sign_up_layout).setVisibility(View.GONE);

        final boolean startsWithVowel =
            "aeiou".indexOf(Character.toLowerCase(mUserRole.name().charAt(0))) != -1;

        final TextView notUserRole = findViewById(R.id.label_not_user_role);

        notUserRole.setPaintFlags(notUserRole.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        formatLabelWithRole(
            notUserRole.getId(),
            startsWithVowel ? R.string.not_an_user_role : R.string.not_a_user_role
        );
        formatLabelWithRole(R.id.hello_label, R.string.hello_user_role);


        PhoneNumberHelper.addFormatting(mPhoneEditText = findViewById(R.id.input_phone));
    }

    public
    void onToSignUp(final View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public
    void onToLanding(final View view) {
        finish();
    }

    private
    void formatLabelWithRole(final int viewId, final int stringId) {
        ((TextView) findViewById(viewId)).setText(
            String.format(getString(stringId), mUserRole.name())
        );
    }

    public
    void onSignIn(final View view) {
        final String phoneNumber = PhoneNumberHelper.normalize(mPhoneEditText.getText());
        final String password = mUserPasswordEditText.getText().toString();

        AuthManager authManager = AuthManager.getManager();
        authManager.signIn(this, phoneNumber, password, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                EntityFactory entityFactory = new EntityFactory();
                User user = (User) entityFactory.getEntity(response, User.class);
                System.out.println(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
    }
}
