package com.nhommot.doctruyen.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.ImageAccount;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    TextView tvCaiDat, tvCapNhat;
    ImageView imgTaiKhoan;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference root;

    private StorageTask mUploadTask;
    private List<ImageAccount> imageAccounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan_layout);
        addControls();
        addEvents();
    }

    private void addEvents() {
        tvCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        imgTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        tvCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {

            if (mImageUri != null) {
                Calendar calendar = Calendar.getInstance();
                StorageReference fileReference = mStorageRef.child("image" + calendar.getTimeInMillis() + ".png");

                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                Toast.makeText(AccountActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                ImageAccount upload = new ImageAccount("Quang Chien", taskSnapshot.getDownloadUrl().toString());



                                root.child("ImageAccount").push().setValue(upload);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AccountActivity.this, "loi o day " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            }
                        });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
    }

//    private boolean searchImageAccount() {
//
//        Query query = root.child("ImageAccount").equalTo("Quang Chin");
//        if(query!=null){
//            return true;
//        }
//        else return false;
//    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void loadImageAccount() {

        root.child("ImageAccount").orderByChild("userName").equalTo("Quang Chien").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            String imageUrl = data.getValue(ImageAccount.class).getImageURL();
                            Picasso.with(AccountActivity.this).load(imageUrl).into(imgTaiKhoan);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imgTaiKhoan);
        }
    }

    private void addControls() {
        tvCaiDat = findViewById(R.id.tvCaiDat);
        tvCapNhat = findViewById(R.id.tvCapNhat);
        imgTaiKhoan = findViewById(R.id.imgTaiKhoan);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageAccount");
        root = FirebaseDatabase.getInstance().getReference();
        loadImageAccount();
    }

}
