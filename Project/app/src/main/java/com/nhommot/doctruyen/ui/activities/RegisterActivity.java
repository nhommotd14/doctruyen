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
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        final String userName = email;
        final String firstName = edtFirstName.getText().toString();
        final String lastName = edtLastName.getText().toString();
        final String image = "https://i.pinimg.com/originals/18/47/7b/18477b6021da0bc5ce4e54000118818f.png";
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
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
        if(!email.matches(emailPattern))
        {
            return "Vui lòng nhập đúng định dạng email !";
        }
        return "";
    }
}
