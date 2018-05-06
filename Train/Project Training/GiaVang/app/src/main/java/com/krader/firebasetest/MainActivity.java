package com.krader.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = firebaseDatabase.getReference();
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText5);
        editText2 = findViewById(R.id.editText6);
        editText3 = findViewById(R.id.editText7);
        editText4 = findViewById(R.id.editText8);
        aSwitch = findViewById(R.id.switch1);
        // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //  DatabaseReference myRef = database.getReference("message");
        // myRef.setValue("Hello, World!");
        //  rootRef.
        // editText1=rootRef.child("18k").setValue("edi")
        // editText1 = rootRef.child("18k").

        rootRef.child("18k").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  editText1.setText((dataSnapshot.getValue(String.class)));
                String string = dataSnapshot.getValue().toString();
                editText1.setText(string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO lam sau
            }
        });
        rootRef.child("9999").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  editText1.setText((dataSnapshot.getValue(String.class)));
                String string = dataSnapshot.getValue().toString();
                editText3.setText(string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO lam sau
            }
        });
        rootRef.child("USD").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  editText1.setText((dataSnapshot.getValue(String.class)));
                String string = dataSnapshot.getValue().toString();
                editText2.setText(string);
                Log.i("hello", "hello");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO lam sau
            }
        });
        rootRef.child("AUD").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  editText1.setText((dataSnapshot.getValue(String.class)));
                String string = dataSnapshot.getValue().toString();
                editText4.setText(string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO lam sau
            }
        });
        if (!aSwitch.isChecked()) {
            editText1.setEnabled(false);
            editText2.setEnabled(false);
            editText3.setEnabled(false);
            editText4.setEnabled(false);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Log.i("________________", String.valueOf(isChecked));
                    editText1.setEnabled(false);
                    editText2.setEnabled(false);
                    editText3.setEnabled(false);
                    editText4.setEnabled(false);
                    rootRef.child("18k").setValue(editText1.getText().toString());
                    rootRef.child("USD").setValue(editText2.getText().toString());
                    rootRef.child("9999").setValue(editText3.getText().toString());
                    rootRef.child("AUD").setValue(editText4.getText().toString());
                } else {
                    editText1.setEnabled(true);
                    editText2.setEnabled(true);
                    editText3.setEnabled(true);
                    editText4.setEnabled(true);
                }
            }
        });
    }
}
