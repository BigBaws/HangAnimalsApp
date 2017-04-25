package com.bigbaws.hanganimals.frontend.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.frontend.MainMenuActivity;
import com.facebook.AccessToken;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends AppCompatActivity {

    /* SOAP */
    private static final String NAMESPACE = "http://javabog.dk:9901/brugeradmin";
    private static final String URL ="http://javabog.dk:9901/brugeradmin?wsdl";
    private static final String SOAP_ACTION = "https://api.authorize.net/soap/v1/AuthenticateTest";
    private static final String METHOD_NAME = "hentBruger";

    /* Log */
    private static final String TAG = "";

    protected TextView test;
    protected EditText username_input, password_input;
    protected Button login_button;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);

        setContentView(R.layout.login_activity);

        /* Views */
        test = (TextView) findViewById(R.id.test);
        username_input = (EditText) findViewById(R.id.login_email);
        password_input = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_btn_login);

        /* Normal LoginActivity */
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_input.getText().toString().trim();
                password = password_input.getText().toString().trim();

                soap.addProperty("arg0", username);
                soap.addProperty("arg1", password);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(soap);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    //SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                    // SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                    SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;


                    test.setText(resultsRequestSOAP.toString());
                    System.out.println("Response::"+resultsRequestSOAP.toString());


                } catch (Exception e) {
                    System.out.println("Error"+e);
                }

            }
        });
    }

}