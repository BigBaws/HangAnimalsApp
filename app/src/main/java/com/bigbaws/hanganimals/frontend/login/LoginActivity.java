package com.bigbaws.hanganimals.frontend.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.asynctasks.LoginAsync;
import com.bigbaws.hanganimals.backend.util.PayPalController;
import com.paypal.android.sdk.payments.PayPalService;


public class LoginActivity extends AppCompatActivity {


    protected EditText username_input, password_input;
    protected Button login_button;

    private String username;
    private String password;
    private String endPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        /* Views */
        username_input = (EditText) findViewById(R.id.login_email);
        password_input = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_btn_login);


        /* Normal LoginActivity */
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();

                username = username_input.getText().toString().trim();
                password = password_input.getText().toString().trim();

                endPath = "/login";

                new LoginAsync(LoginActivity.this).execute(username, password, endPath);
//                new LoginAsync(LoginActivity.this).execute();

                Intent intent = new Intent(LoginActivity.this, PayPalService.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalController.config);
                startService(intent);

            }
        });
    }

    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(username_input.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(password_input.getWindowToken(), 0);
        }
    }

}


