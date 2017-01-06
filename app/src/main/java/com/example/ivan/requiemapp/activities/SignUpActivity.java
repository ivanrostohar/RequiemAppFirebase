package com.example.ivan.requiemapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends AppCompatActivity {

    private EditText edt_name, edt_address, edt_phone, edt_email, edt_password;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String email, password;
    private TextInputLayout til_email, til_password;
    private CoordinatorLayout coordinatorLayoutSignUp;
    private Snackbar snackbar;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_phone = (EditText) findViewById(R.id.edt_phone_contact);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        til_email = (TextInputLayout)findViewById(R.id.til_email);
        til_password = (TextInputLayout)findViewById(R.id.til_password);
        coordinatorLayoutSignUp = (CoordinatorLayout)findViewById(R.id.coorinator_layout_signup);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = edt_email.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                user = new UserModel(edt_name.getText().toString().trim(),
                        edt_address.getText().toString().trim(),
                        email,
                        edt_phone.getText().toString().trim()
                        );

                if (TextUtils.isEmpty(email)) {
                    til_email.setError(getString(R.string.msg_email_error));
                    return;
                }else{
                    til_email.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(password)) {
                    til_password.setError(getString(R.string.msg_password_error));
                    return;
                }else{
                    til_password.setErrorEnabled(false);
                }

                progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                          //  Log.v("OnComplete: ", task.getException().getLocalizedMessage());

                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                                FirebaseUser userId = firebaseAuth.getCurrentUser();
                                databaseReference.child(userId.getUid()).setValue(user);

                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }
                            else{
                                snackbar = Snackbar.make(btnSignUp, getString(R.string.msg_signUp), Snackbar.LENGTH_INDEFINITE)
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

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
