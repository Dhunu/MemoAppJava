package com.angel.memoappjava;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    //Initialize fields
    Button btnSignUp;
    EditText etName, etEmail, etPassword, etConfirmPassword;
    TextView tvSignIn;

    //Initialize FirebaseAuth
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Initialize fireStore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        btnSignUp = findViewById(R.id.btn_sign_up);
        etName = findViewById(R.id.name_sign_up);
        etEmail = findViewById(R.id.email_sign_up);
        etPassword = findViewById(R.id.password_sign_up);
        etConfirmPassword = findViewById(R.id.confirm_password_sign_up);
        tvSignIn = findViewById(R.id.sign_in_txt);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void createUser() {
        String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        final HashMap<String, String> user = new HashMap<String,String>();
        user.put("name",name);
        user.put("email",email);
        user.put("password",password);

        if (name.isEmpty()){
            etName.requestFocus();
            Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            Toast.makeText(this,"Please enter password again",Toast.LENGTH_SHORT).show();
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password don't match", Toast.LENGTH_LONG).show();
        }

        else {
            mAuth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean isNewUser = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getSignInMethods()).isEmpty();
                            if (isNewUser){
                                try {
                                    mAuth.createUserWithEmailAndPassword(email,password)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        String userID = mAuth.getUid();
                                                        assert userID != null;
                                                        db.collection(userID).document("ProfileData").set(user);
                                                        finish();
                                                    }else{
                                                        Toast.makeText(getBaseContext(),"Sign Up failed. Try again after some time.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                Log.d(TAG,"Email Exists");
                            }
                        }
                    });

        }
    }

    private void saveUserData(){

    }
}
