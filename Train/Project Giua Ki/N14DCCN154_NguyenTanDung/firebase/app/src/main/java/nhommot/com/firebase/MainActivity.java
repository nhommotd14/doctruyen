package nhommot.com.firebase;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nhommot.com.firebase.model.item;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView img;
    private TextView tvTenTruyen;
    private TextView tvTacGia;
    private TextView tvSoChuong;
    private TextView tvTheLoai;
    private TextView tvReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPage(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tvTenTruyen = (TextView) findViewById(R.id.tvTenTruyen);
        tvTacGia = (TextView) findViewById(R.id.tvTacGia);
        tvTheLoai = (TextView) findViewById(R.id.tvTheLoai);
        tvSoChuong = (TextView) findViewById(R.id.tvSoChuong);
        mDatabase = FirebaseDatabase.getInstance().getReference("N14DCCN154");

        ValueEventListener itemListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item truyen = dataSnapshot.getValue(item.class);
                tvSoChuong.setText("Số chương : " + String.valueOf(truyen.getSoChuong()));
                tvTacGia.setText("Tác giả : " + truyen.getTacGia());
                tvTenTruyen.setText(truyen.getTenTruyen());
                tvTheLoai.setText("Thể Loại : " + truyen.getTheLoai());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(itemListener);
    }

    private void setupViewPage(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReviewFragment(), "Review");
        adapter.addFragment(new CommentFragment(), "Comment");
        viewPager.setAdapter(adapter);
    }
}
