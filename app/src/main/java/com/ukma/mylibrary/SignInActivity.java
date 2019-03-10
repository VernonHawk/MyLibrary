package com.ukma.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public
class SignInActivity extends AppCompatActivity {

    private Role userRole;

    @Override protected
    void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userRole = (Role) getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        formatLabelWithRole(R.id.hello_label, R.string.hello_user_role);
        formatLabelWithRole(R.id.label_not_user_role, R.string.not_a_user_role);

        if (userRole != Role.Reader)
            findViewById(R.id.to_sign_up_layout).setVisibility(View.GONE);
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
            String.format(getString(stringId), userRole.name())
        );
    }
}
