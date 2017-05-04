package com.example.a6sigma.great4ip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a6sigma.great4ip.FragmentMessage.DraftFragment;
import com.example.a6sigma.great4ip.FragmentMessage.InboxFragment;
import com.example.a6sigma.great4ip.FragmentMessage.SentFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);

        fabTambah = (FloatingActionButton) findViewById(R.id.ic_tambah);

        final TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "halo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CreateMessageActivity.class);
                startActivity(intent);
            }
        });

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {

                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add("Inbox", new InboxFragment());
        adapter.add("Sent", new SentFragment());
        adapter.add("Draft", new DraftFragment());
        viewPager.setAdapter(adapter);
    }

    // ADAPTER VIEWPAGER
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void add(String title, Fragment fragment) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
