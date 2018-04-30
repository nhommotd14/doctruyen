package com.nhommot.doctruyen.ui.activities;

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
    EditText edtEmail, edtPassword, edtName, edtImage, edtAge;
    RadioButton radionButton;
    RadioGroup radioGroup;

    FirebaseAuth mAuthencation;

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
                String flg = checkValidate(edtEmail.getText().toString(), edtPassword.getText().toString(), edtName.getText().toString(), edtAge.getText().toString());
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
        final String username = edtName.getText().toString();
        final String image = edtImage.getText().toString();
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
                            User data = new User(username, age, sex, image);
                            mData.child("User").child(uid).setValue(data);
                            Toast.makeText(RegisterActivity.this, "Đăng Ký Thành Công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void AnhXa() {
        btnDangKy = (Button) findViewById(R.id.buttonDangKy);
        edtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);
        edtName = (EditText) findViewById(R.id.editTextName);
        edtImage = (EditText) findViewById(R.id.editTextImage);
        edtAge = (EditText) findViewById(R.id.editTextAge);
        radioGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radionButton = (RadioButton) findViewById(selectedId);
    }

    private String checkValidate(String email, String pass, String name, String age) {
        if (name.equals("")) {
            return "Vui lòng nhập tên !";
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
