package com.example.tungmai.feedy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.custominterface.LoadRefressBlog;
import com.example.tungmai.feedy.dialog.ListPreparDialog;
import com.example.tungmai.feedy.fragment.BlogFragment;
import com.example.tungmai.feedy.fragment.FeedySaveFragment;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.fragment.HomeFragment;
import com.example.tungmai.feedy.fragment.ProfileFragment;
import com.example.tungmai.feedy.models.User;
import com.example.tungmai.feedy.service.FeedyService;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by TungMai on 3/15/2017.
 */

public class HomeActivity extends AppCompatActivity {


    private static final String TAG = "HomeActivity";
    private static final String TAG_FRAGMENT_HOME = "tag_fragment_home";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    //    private TabLayout tabLayout;
    private User user;

    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FeedySaveFragment feedySaveFragment;
    private TextView tvNameUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent myIntent = new Intent(HomeActivity.this, FeedyService.class);
        // Gọi phương thức startService (Truyền vào đối tượng Intent)
        this.startService(myIntent);


        user = (User) getIntent().getSerializableExtra(FragmentLogin.INTENT_USER);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedy");


//        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        feedySaveFragment = new FeedySaveFragment();

        initFragmentHome();
        initDrawerLayout();
//        initTabsLayout();
    }


    public void initFragmentHome() {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_HOME);
//        if(fragment != null)
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, homeFragment, TAG_FRAGMENT_HOME);
        fragmentTransaction.commit();
    }

    public void initFragmentProfile() {
//        profileFragment = new ProfileFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, profileFragment);
        fragmentTransaction.commit();
    }

    public void initFragmentSaveFeedy() {
//        feedySaveFragment = new FeedySaveFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, feedySaveFragment);
        fragmentTransaction.commit();
    }


//    private void initTabsLayout() {
//        tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//
//        tabLayout.addTab(tabLayout.newTab().setText("Diễn đàn"));
//        tabLayout.addTab(tabLayout.newTab().setText("Nấu ăn"));
//        tabLayout.addTab(tabLayout.newTab().setText("Mẹo làm bếp"));
//
//        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.color_text_tablayout));
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
//        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroudTabLayout));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        PageTabAdapter pageTabAdapter = new PageTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pageTabAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }

    private void initDrawerLayout() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemTextColor(getResources().getColorStateList(R.color.color_text_navigation));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        getSupportActionBar().setTitle("Feedy");
                        initFragmentHome();
//                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        getSupportActionBar().setTitle("Thông tin cá nhân");
                        initFragmentProfile();
                        drawerLayout.closeDrawers();
                        break;
//                    case R.id.message:
//                        drawerLayout.closeDrawers();
//                        break;
                    case R.id.list_feedy:
                        getSupportActionBar().setTitle("Danh sách nấu ăn");
                        initFragmentSaveFeedy();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.list_prepare:
                        ListPreparDialog listPreparDialog = new ListPreparDialog();
//                listPreparDialog.setStyle(android.R.style.Theme_Holo_Dialog_NoActionBar,R.);
                        listPreparDialog.show(getFragmentManager(), "PrepareDialog");
//                        Toast.makeText(getApplicationContext(), "Saved news", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                }

                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        final ImageView ivUser = (ImageView) header.findViewById(R.id.iv_image);
        Glide.with(this).load(ConnectSever.IP_SEVER + user.getImageUser()).into(ivUser);

        tvNameUser = (TextView) header.findViewById(R.id.tv_email);
        tvNameUser.setText(user.getName());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public User getUser() {
        return user;
    }

    private LoadRefressBlog loadRefressBlog;

    public void setLoadRefressBlog(LoadRefressBlog loadRefressBlog) {
        this.loadRefressBlog = loadRefressBlog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BlogFragment.REQUEST_CODE_LOGIN) {
                loadRefressBlog.loadRefress();
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
