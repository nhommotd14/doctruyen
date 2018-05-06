package com.nhommot.doctruyen.ui.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.ui.fragments.BookOfflineFragment;
import com.nhommot.doctruyen.ui.fragments.FavouriteFragment;
import com.nhommot.doctruyen.ui.fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";
    private DrawerLayout drawer;
    FirebaseAuth firebaseAuth;
    Button login, register;

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        }

        Fragment fragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

//        handleIntent(getIntent());
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null) {
//            actionbar.setDisplayHomeAsUpEnabled(true);
//        }
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Fragment fragment = null;
//        Class fragmentClass = null;
//
//
//        if (id == R.id.nav_home) {
//            // Handle the camera action
//            fragmentClass = MainFragment.class;
//        } else if (id == R.id.nav_history) {
//
//        } else if (id == R.id.nav_download) {
//
//        } else if (id == R.id.nav_favourite) {
//            //Log.d(TAG, "onNavigationItemSelected: nav_manage clicked");
//            fragmentClass = FavouriteFragment.class;
//        } else if (id == R.id.nav_setting) {
//            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
//            startActivity(intent);
//        }
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "onNavigationItemSelected: " + fragment);
//
//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
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
            case R.id.nav_history:
                //TODO add fragment or start activity for History
                break;
            case R.id.nav_download:
                //TODO add fragment or start activity for Download
                fragment = new BookOfflineFragment();
                break;
            case R.id.nav_favourite:
                fragment = new FavouriteFragment();
                break;
            case R.id.nav_setting:
                intent = new Intent(MainActivity.this, AccountActivity.class);
                break;
            case R.id.nav_about:
                //TODO add fragment or start activity for About
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
}
