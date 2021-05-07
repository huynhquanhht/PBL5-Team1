package com.example.plb.ui.Home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.plb.R;
import com.example.plb.model.Info;
import com.example.plb.model.Schedule;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.classroom.ClassRoomActivity;
import com.example.plb.ui.history.HistoryActivity;
import com.example.plb.ui.infor.InforActivity;
import com.example.plb.ui.login.MainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final String url = "https://plb5.000webhostapp.com/getSchedule.php";
    private final String infoUrl = "http://plb5.000webhostapp.com/getInfo.php";

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private ImageView mNavButton;
    private ImageView mAvatarMainImageView;
    private ImageView mAvatarImageView;
    private ProgressDialog mLoadingBar;
    private Button mAttendanceButton;
    private RecyclerView mRecyclerView;
    private List<Schedule> mScheduleList = new ArrayList<>();
    private Toolbar mToolbar;
    private TextView mPhoneTextView;
    private TextView mNameTextView;
    private TextView mBirthDayTextView;
    private Info mInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        getInfo(infoUrl);

    }


    public void getInfo(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject explrObject = jsonArray.getJSONObject(0);

                    String id = explrObject.getString("id");
                    String name = explrObject.getString("name");
                    String email = explrObject.getString("email");
                    String phone = explrObject.getString("phone");
                    String sex = explrObject.getString("sex");
                    String birthday = explrObject.getString("birthday");
                    String url = explrObject.getString("url");

                    mInfo = new Info(id, name, email, phone, sex, birthday, url);

                    Glide.with(getApplicationContext()).load(mInfo.getUrl())
                            .placeholder(R.drawable.loading)
                            .into(mAvatarMainImageView);
                    mPhoneTextView.setText("Phone: " + mInfo.getPhone());
                    mNameTextView.setText(mInfo.getName());
                    mBirthDayTextView.setText("BirthDay: " + mInfo.getBirthDay());

                    mLoadingBar.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("bug", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Bug", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id = Prevalent.currentOnlineUser.getIdInfo();

                Map<String, String> params = new HashMap<>();
                params.put("id", id);

                return params;
            }
        };

        requestQueue.add(request);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navView);
        mNavigationView = findViewById(R.id.navView);
        mAvatarMainImageView = findViewById(R.id.avatarMainImageView);
        mAvatarImageView = findViewById(R.id.avatarImageView);
        mPhoneTextView = findViewById(R.id.phoneTextView);
        mBirthDayTextView = findViewById(R.id.birthDayTextView);
        mNameTextView = findViewById(R.id.nameMainTextView);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mLoadingBar = new ProgressDialog(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_profile: {
                Intent intent = new Intent(HomeActivity.this, InforActivity.class);
                intent.putExtra("idInfo", Prevalent.currentOnlineUser.getIdInfo());
                startActivity(intent);
                break;
            }
            case R.id.nav_history: {
                Intent intent1 = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent1);
                break;
            } case R.id.logout: {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}