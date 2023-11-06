package com.angel.memoappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    Button btnLogin;
    EditText etEmail, etPassword;
    TextView tvForgotPassword, tvSignUp;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.email_sign_in);
        etPassword = findViewById(R.id.password_sign_in);
        tvForgotPassword = findViewById(R.id.forgot_password);
        tvSignUp = findViewById(R.id.sign_up_txt);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signInUser() {
        String email = etEmail.toString().trim();
        String password = etPassword.toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please type email", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please type password", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DisplayActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }

}
