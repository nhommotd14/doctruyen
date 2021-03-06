package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhommot.doctruyen.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText edtEmail, edtPassword;
    ImageView imgView;
    TextView link;


    FirebaseAuth mAuthencation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthencation = FirebaseAuth.getInstance();

        AnhXa();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = checkValidate(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
                if (flag.equals("")) {
                    DangNhap();
                } else {
                    Toast.makeText(LoginActivity.this, flag, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void DangNhap() {
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        mAuthencation.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void AnhXa() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.comic);
        link = (TextView) findViewById(R.id.link);
    }

    private String checkValidate(String email, String password) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.equals("")) {
            return "Vui lòng nhập email !";
        }
        if (password.equals("")) {
            return "Vui lòng nhập password !";
        }
        if(!email.matches(emailPattern))
        {
            return "Vui lòng nhập đúng định dạng email !";
        }
        return "";
    }

}
