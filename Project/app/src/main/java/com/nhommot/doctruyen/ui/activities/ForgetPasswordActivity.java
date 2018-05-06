package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nhommot.doctruyen.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageView imgView;
    EditText edtResetPass;
    Button btnSend;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        AnhXa();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flag = checkValidate(edtResetPass.getText().toString());
                if (flag.equals("")) {
                    ResetPass();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, flag, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void ResetPass() {
        String email = edtResetPass.getText().toString();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Email sent ! Please check your email ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Can not send email, Your email not exist !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void AnhXa() {
        btnSend = (Button) findViewById(R.id.btnSend);
        edtResetPass = (EditText) findViewById(R.id.edtForgetPass);
        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.forgetpass);
    }

    private String checkValidate(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.equals("")) {
            return "Vui lòng nhập email !";
        }
        if (!email.matches(emailPattern)) {
            return "Vui lòng nhập đúng định dạng email !";
        }
        return "";
    }
}
