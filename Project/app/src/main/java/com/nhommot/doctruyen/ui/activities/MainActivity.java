package com.nhommot.doctruyen.ui;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.FirebaseDatabase;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.AuthorFirebaseManager;
import com.nhommot.doctruyen.database.BookFirebaseManager;
import com.nhommot.doctruyen.database.OnGetDataListener;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.Type;
import com.nhommot.doctruyen.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        test();
    }

    public void test(){
//        Author author = new Author();
//        author.setName("Huy Tran");
//        author.setDesciption("My name is Huy Tran");
//        AuthorFirebaseManager authorFirebaseManager = new AuthorFirebaseManager();

//        Set data to firebase
//        author = authorFirebaseManager.addAuthor(author);
//        Log.d(TAG, "test: " + JsonUtils.encode(author));

//        Get data from firebase
//        final Author[] authorFromFirebase = {new Author()};
//        authorFirebaseManager.getAuthor(author.getAuthorId(), new OnGetDataListener() {
//            @Override
//            public void onSuccess(Object data) {
//                authorFromFirebase[0] = (Author) data;
//                Log.d(TAG, "onSuccess: ===================\n" + JsonUtils.encode(authorFromFirebase[0]));
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFailed(Object data) {
//
//            }
//        });
//        Book book = new Book();
//        book.setAuthor(author);
//        book.setDescription("This is description");
//        book.setImgPreview("http://xnxx.com");
//        book.setName("Doraemon");
//        book.setViews(10000);
//        Map<String, Boolean> map1 = new HashMap<>();
//        map1.put("Fiction", true);
//        map1.put("Incest", true);
//        book.setTypes(map1);
        BookFirebaseManager bookFirebaseManager = new BookFirebaseManager();
//        book = bookFirebaseManager.addBook(book);
        bookFirebaseManager.getBook("-L9pMvlQUc11i0Jvmpl3");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
