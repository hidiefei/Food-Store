package com.example.me.firebase.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me.firebase.MainActivity;
import com.example.me.firebase.R;
import com.example.me.firebase.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp, btnResetPassword;
    private EditText edtUsername, edtPassword, edtConfirmPass, edtMail, edtPhone;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtMail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isOk = true;
                String email = edtMail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String userName = edtUsername.getText().toString();
                String confirmPass = edtConfirmPass.getText().toString();
                String phone = edtPhone.getText().toString();

                if (userName.length() == 0 || password.length() == 0 || email.length() == 0 || phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillAllData), Toast.LENGTH_SHORT).show();
                }   else {
                    if (!EmailValidate.IsOk(email)) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrongFormatEmail), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (phone.length() < 10 || phone.length() > 11) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.phoneLength), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordLength), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.equals(confirmPass)) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordDontMatch), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
}
