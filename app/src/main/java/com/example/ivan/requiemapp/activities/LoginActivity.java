package com.example.ivan.requiemapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.requiemapp.R;
import com.example.ivan.requiemapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_email, edt_password;
    private ProgressBar progressBar;
    private Button btnSignUp, btnLogin, btnReset;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private String email, password;
    private TextInputLayout til_email, til_password;
    private CoordinatorLayout coordinatorLayoutLogin;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_password = (EditText)findViewById(R.id.edt_password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnSignUp = (Button)findViewById(R.id.btn_sign_up);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnReset = (Button)findViewById(R.id.btn_reset_password);
        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_password = (TextInputLayout) findViewById(R.id.til_password);
        coordinatorLayoutLogin = (CoordinatorLayout)findViewById(R.id.coordinator_layout_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    til_email.setError(getString(R.string.msg_email_error));
                    return;
                }else{
                    til_email.setErrorEnabled(false);
                }
                if(TextUtils.isEmpty(password)){
                    til_password.setError(getString(R.string.msg_password_error));
                    return;
                }else {
                    til_password.setErrorEnabled(false);
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            snackbar = Snackbar.make(btnLogin, getString(R.string.msg_login), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            snackbar.dismiss();
                                        }
                                    });
                            snackbar.setActionTextColor(Color.GREEN);
                            TextView snackabarText = (TextView)snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                            snackabarText.setTextColor(Color.YELLOW);
                            snackbar.show();
                        }
                    }
                });


            }
        });
    }
}
