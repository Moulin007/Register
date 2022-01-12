package com.example.registor.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email, password;
    MaterialButton loginBtn;

    ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v -> {

            String UserEmail = email.getText().toString();
            String UserPassword = password.getText().toString();

            progressDialog.show();

            if (TextUtils.isEmpty(UserEmail)){

                email.setError("Please enter email address");

            }
            if (TextUtils.isEmpty(UserPassword)){

                password.setError("Please enter password");

            }
            if (!TextUtils.isEmpty(UserEmail) && !TextUtils.isEmpty(UserPassword)){

                auth.signInWithEmailAndPassword(UserEmail, UserPassword).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainScreen.class));
                        finish();

                    }else {

                        Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();

                    }

                });

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null){

            startActivity(new Intent(LoginActivity.this, MainScreen.class));
            finish();

        }

    }
}