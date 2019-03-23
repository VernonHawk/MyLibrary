package com.ukma.mylibrary;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public
class SignInActivity extends AppCompatActivity {

    private Role mUserRole;

    private EditText mPhoneEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUserRole = (Role) getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);

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
        final String phoneNumber = PhoneNumberHelper.normalize(mPhoneEditText.getText());

        // ...
    }
}
