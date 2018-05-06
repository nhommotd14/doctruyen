package com.nhommot.doctruyen.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.ImageAccount;
import com.nhommot.doctruyen.models.User;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountActivity extends AppCompatActivity {
    private static final String TAG ="" ;
    TextView tvCaiDat, tvCapNhat,tvTenTaiKhoan,tvHoTen;
    RelativeLayout layoutCaiDat,layoutChinhSach,layoutAbout,layoutDangXuat,layoutTaiKhoan,layoutThongTinTaiKhoan;
    CircleImageView imgTaiKhoan;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference root;
    private String uIdAccount;
    private StorageTask mUploadTask;
    private List<ImageAccount> imageAccounts;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.taikhoan_layout);
        FirebaseUser user = mAuth.getCurrentUser();

        if (user!=null){
            uIdAccount = user.getUid();
            addControls();
            addEvents();
        }
        else {
            Toast.makeText(AccountActivity.this,"Vui lòng đăng nhập để sử dụng các chức năng",Toast.LENGTH_LONG);
        }

            layoutCaiDat = findViewById(R.id.layoutCaiDat);
            layoutAbout = findViewById(R.id.layoutAbout);
            layoutCaiDat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AccountActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });
            layoutAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowAlertDialogAbout();
                }
            });

    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    private void addEvents() {

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



        layoutThongTinTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialogAccount();
            }
        });


        layoutDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }


    private void loadDialogAccount() {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.setTitle("Account");
        dialog.setContentView(R.layout.dialog_account);
        final EditText edUsernameAcc = dialog.findViewById(R.id.edUsernameAcc);
        final EditText edFirstNameAcc = dialog.findViewById(R.id.edFirstNameAcc);
        final EditText edLastNameAcc = dialog.findViewById(R.id.edLastNameAcc);
        final EditText edAddressAcc = dialog.findViewById(R.id.edAddressAcc);
        final EditText edPhoneAcc = dialog.findViewById(R.id.edPhoneAcc);
        final EditText edTuoi = dialog.findViewById(R.id.edTuoi);
        final CircleImageView circleImageTaiKhoan = dialog.findViewById(R.id.circleImgTaiKhoan);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnThoatAccount = dialog.findViewById(R.id.btnThoatAccount);
        final User user = new User();
        root.child("User").child(uIdAccount).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        edUsernameAcc.setText(dataSnapshot.getValue(User.class).getUsername());
                        edFirstNameAcc.setText(dataSnapshot.getValue(User.class).getFirstName());
                        edLastNameAcc.setText(dataSnapshot.getValue(User.class).getLastName());
                        edTuoi.setText(dataSnapshot.getValue(User.class).getAge()+"");
                        edAddressAcc.setText(dataSnapshot.getValue(User.class).getAddress());
                        edPhoneAcc.setText(dataSnapshot.getValue(User.class).getPhoneNumber());

                        String imageUrl = dataSnapshot.getValue(User.class).getImgURL();
                        Picasso.get().load(imageUrl).into(circleImageTaiKhoan);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                user.setUsername(edUsernameAcc.getText()+"");
//                user.setFirstName(edFirstNameAcc.getText()+"");
//                user.setLastName(edLastNameAcc.getText()+"");
//                user.setAge(Integer.parseInt(String.valueOf(edTuoi.getText())));
//                user.setAddress(edAddressAcc.getText()+"");
//                user.setPhoneNumber(edPhoneAcc.getText()+"");
//                root.child("User").child(uIdAccount).setValue(user);
                root.child("User").child(uIdAccount).child("username").setValue(edUsernameAcc.getText()+"");
                root.child("User").child(uIdAccount).child("firstName").setValue(edFirstNameAcc.getText()+"");
                root.child("User").child(uIdAccount).child("lastName").setValue(edLastNameAcc.getText()+"");
                root.child("User").child(uIdAccount).child("age").setValue(Integer.valueOf(edTuoi.getText().toString()));
                root.child("User").child(uIdAccount).child("address").setValue(edAddressAcc.getText()+"");
                root.child("User").child(uIdAccount).child("phoneNumber").setValue(edPhoneAcc.getText()+"");

                dialog.hide();
            }
        });
        btnThoatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        dialog.show();
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
                                //ImageAccount upload = new ImageAccount("Quang Chien", taskSnapshot.getDownloadUrl().toString());
                                String imgURL=taskSnapshot.getDownloadUrl().toString();



                                root.child("User").child(uIdAccount).child("imgURL").setValue(imgURL);
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

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void loadImageAccount() {

        root.child("User").child(uIdAccount).
               addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       String imageUrl = dataSnapshot.getValue(User.class).getImgURL()+"";
                       String userName = dataSnapshot.getValue(User.class).getUsername()+"";
                       String firstName = dataSnapshot.getValue(User.class).getFirstName()+"";
                       String lastName = dataSnapshot.getValue(User.class).getLastName()+"";
                       tvTenTaiKhoan.setText(userName);
                       tvHoTen.setText(firstName+" "+lastName);
                       Picasso.get().load(imageUrl).into(imgTaiKhoan);
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

            Picasso.get().load(mImageUri).into(imgTaiKhoan);
        }
    }

    private void addControls() {
        tvCaiDat = findViewById(R.id.tvCaiDat);
        tvCapNhat = findViewById(R.id.tvCapNhat);
        tvTenTaiKhoan = findViewById(R.id.tvTenTaiKhoan);
        tvHoTen = findViewById(R.id.tvHoTen);
        imgTaiKhoan = findViewById(R.id.imgTaiKhoan);
        layoutAbout = findViewById(R.id.layoutAbout);
        layoutChinhSach = findViewById(R.id.layoutChinhSach);
        layoutDangXuat = findViewById(R.id.layoutLogout);
        layoutCaiDat = findViewById(R.id.layoutCaiDat);
        layoutThongTinTaiKhoan = findViewById(R.id.layoutThongTinTaiKhoan);
        layoutTaiKhoan = findViewById(R.id.layoutTaiKhoan);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageAccount");
        root = FirebaseDatabase.getInstance().getReference();
        loadImageAccount();
    }


    public void ShowAlertDialogAbout()
    {
        String[] arrTeam=getResources().getStringArray(R.array.DreamTeam);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Danh Sách Thành Viên");

        dialogBuilder.setItems(arrTeam, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

}
