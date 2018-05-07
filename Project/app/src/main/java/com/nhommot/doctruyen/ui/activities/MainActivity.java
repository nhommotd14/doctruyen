package com.nhommot.doctruyen.ui.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.models.ContentOffline;
import com.nhommot.doctruyen.models.User;
import com.nhommot.doctruyen.ui.fragments.BookOfflineFragment;
import com.nhommot.doctruyen.ui.fragments.FavouriteFragment;
import com.nhommot.doctruyen.ui.fragments.MainFragment;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";
    private DrawerLayout drawer;
    Button login, register;
    TextView email, name;
    CircleImageView avataUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference root = firebaseDatabase.getReference();
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        Log.d(TAG, "onCreate: "+!isInternetAvailable());
        SharedPrefsUtils.setOfflineState(this,!isInternetAvailable());

        //set actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();
    }

    private void createDB() {
        final BookOfflineSQLite dbOffline=new BookOfflineSQLite(getApplicationContext(),"OfflineBook.sqlite",null,1);
        String tableBook="create table if not exists bookoff(id nvarchar,name nvarchar,author nvarchar,description nvarchar,img Blob,type nvarchar,star integer)";
        dbOffline.QueryData(tableBook);
        String tableBookChap="create table if not exists BookChapoff(idtruyen nvarchar,idchap nvarchar,chapname nvarchar)";
        dbOffline.QueryData(tableBookChap);
        String tableChap="create table if not exists Chap(idchap nvarchar,chapnum integer,img Blob)";
        dbOffline.QueryData(tableChap);

//        //Ket noi database
//        Cursor cursor=dbOffline.Getdata("select * from chap where idchap='chapterRandomStr1'");
//        ContentOffline contentOffline=null;
//        while (cursor.moveToNext()){
//            contentOffline= new ContentOffline(
//                    cursor.getString(0),
//                    cursor.getString(1),
//                    cursor.getBlob(2)
//            );
//        }
//        Log.d(TAG, "createDB: f444fdg7///7777////////////"+JsonUtils.encode(contentOffline));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navigationView.removeHeaderView(navigationView.getHeaderView(0));
            navigationView.inflateHeaderView(R.layout.nav_header_logged_null);
            View headerLayout = navigationView.getHeaderView(0);
            login = headerLayout.findViewById(R.id.btn_login);
            register = headerLayout.findViewById(R.id.btn_register);
//            Button button2 = navigationView.get
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);

                    startActivity(intent);
                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            navigationView.removeHeaderView(navigationView.getHeaderView(0));
            navigationView.inflateHeaderView(R.layout.nav_header);
            final View headerLayout = navigationView.getHeaderView(0);
            email = headerLayout.findViewById(R.id.txt_email);
            name = headerLayout.findViewById(R.id.txt_name);
            avataUser = headerLayout.findViewById(R.id.avata_user_nav);
            String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            root.child("User").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name.setText(Objects.requireNonNull(dataSnapshot.getValue(User.class)).getFirstName());
                    email.setText(Objects.requireNonNull(dataSnapshot.getValue(User.class)).getUsername());
                    Picasso.get().load(Objects.requireNonNull(dataSnapshot.getValue(User.class)).getImgURL()).into(avataUser);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        switch (id) {
            case R.id.nav_home:
                fragment = new MainFragment();
                break;
            case R.id.nav_download:
                fragment = new BookOfflineFragment();
                break;
            case R.id.nav_favourite:
                fragment = new FavouriteFragment();
                break;
            case R.id.nav_setting:
                intent = new Intent(MainActivity.this, AccountActivity.class);
                break;
            case R.id.nav_about:
                ShowAlertDialogAbout();
                break;
            default:
                fragment = new MainFragment();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else if (intent != null) {
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void ShowAlertDialogAbout() {
        String[] arrTeam = getResources().getStringArray(R.array.DreamTeam);
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
    public boolean isInternetAvailable() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
