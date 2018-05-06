package com.nhommot.doctruyen.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.User;

public class RegisterActivity extends AppCompatActivity {
    public String uid;
    Button btnDangKy;
    EditText edtEmail, edtPassword, edtFirstName, edtLastName, edtAge;
    RadioButton radionButton;
    RadioGroup radioGroup;

    public static FirebaseAuth mAuthencation;

    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuthencation = FirebaseAuth.getInstance();

        mData = FirebaseDatabase.getInstance().getReference();

        AnhXa();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flg = checkValidate(edtFirstName.getText().toString(), edtLastName.getText().toString(),edtEmail.getText().toString(), edtPassword.getText().toString(), edtAge.getText().toString());
                if (flg.equals("")) {
                    DangKy();
                } else {
                    Toast.makeText(RegisterActivity.this, flg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void DangKy() {
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();
        final String userName = email;
        final String firstName = edtFirstName.getText().toString();
        final String lastName = edtLastName.getText().toString();
        final String image = "https://firebasestorage.googleapis.com/v0/b/doctruyen-697d3.appspot.com/o/ImageAccount%2Faccount2.png?alt=media&token=cd8d5ce8-f18b-48fb-b1e3-5821799d7aa4";
        final int age = Integer.parseInt(edtAge.getText().toString());
        final String sex = radionButton.getText().toString();

        mAuthencation.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                uid = user.getUid();
                            }
                            User data = new User(userName,firstName, lastName, age, image, sex);
                            mData.child("User").child(uid).setValue(data);
                            Toast.makeText(RegisterActivity.this, "Đăng Ký Thành Công !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void AnhXa() {
        btnDangKy = (Button) findViewById(R.id.buttonDangKy);
        edtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);
        edtFirstName = (EditText) findViewById(R.id.editFirstName);
        edtLastName = (EditText) findViewById(R.id.editLastName);
        edtAge = (EditText) findViewById(R.id.editTextAge);
        radioGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radionButton = (RadioButton) findViewById(selectedId);
    }

    private String checkValidate(String fistName, String lastName, String email, String pass,  String age) {
        if (fistName.equals("")) {
            return "Vui lòng nhập Tên !";
        }
        if (lastName.equals("")) {
            return "Vui lòng nhập Họ !";
        }
        if (email.equals("")) {
            return "Vui lòng nhập email !";
        }
        if (pass.equals("")) {
            return "Vui lòng nhập password !";
        }
        if (age.equals("")) {
            return "Vui lòng nhập tuổi !";
        }
        return "";
    }
}
