package com.example.registor.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

    EditText email, password;
    MaterialButton loginBtn;

    TextView loginTxt, loginTitle;
    View loginView;

    ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);

        loginTxt = findViewById(R.id.loginTxt);
        loginTitle = findViewById(R.id.loginTitle);
        loginView = findViewById(R.id.loginView);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        loginBtn = findViewById(R.id.loginBtn);

        email.setTranslationX(800);
        password.setTranslationX(800);
        loginBtn.setTranslationX(800);
        loginTxt.setTranslationX(800);
        loginView.setTranslationX(800);
        loginTitle.setTranslationX(800);

        email.setAlpha(0);
        password.setAlpha(0);
        loginBtn.setAlpha(0);
        loginTxt.setAlpha(0);
        loginView.setAlpha(0);
        loginTitle.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        loginTxt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        loginTitle.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        loginView.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();

        loginBtn.setOnClickListener(v -> {

            String UserEmail = email.getText().toString();
            String UserPassword = password.getText().toString();

            progressDialog.show();

            if (TextUtils.isEmpty(UserEmail)){

                progressDialog.dismiss();

                email.setError("Please enter email address");

            }
            if (TextUtils.isEmpty(UserPassword)){

                progressDialog.dismiss();

                password.setError("Please enter password");

            }
            if (!TextUtils.isEmpty(UserEmail) && !TextUtils.isEmpty(UserPassword)){

                auth.signInWithEmailAndPassword(UserEmail, UserPassword).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        progressDialog.dismiss();

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