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
import androidx.fragment.app.FragmentManager;
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
import com.example.plb.model.Student;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.classroom.ClassRoomActivity;
import com.example.plb.ui.history.HistoryActivity;
import com.example.plb.ui.infor.InforActivity;
import com.example.plb.ui.login.MainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, ClassFragment.NoticeDialogListener {

    private final String infoUrl = "http://103.151.123.96:8000/info/";
    private final String url = "http://103.151.123.96:8000/student/create/";
    private final String urlschedule = "http://103.151.123.96:8000/schedule/create/";

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
    private String baseClass;
    private String idchedule;
    private ClassFragment mClassFragment;
    private FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = getSupportFragmentManager();
        mClassFragment = new ClassFragment();

        init();

        getInfo(infoUrl);

    }


    public void getInfo(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url + Prevalent.currentOnlineUser.getIdInfo();

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
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
        });

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
                intent1.putExtra("idclass", "");
                intent1.putExtra("subject", "");
                startActivity(intent1);
                break;
            } case R.id.logout: {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            } case R.id.nav_import_class: {
                mClassFragment.show(fm, null);
                break;
            }

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void applyFile(String name, String code, String timestart, String timend, String room) {

        baseClass = code;
        updateSchdedule(name, timestart, timend, room, "2", "48");

        Intent intent = new Intent(HomeActivity.this, FilePickerActivity.class);

        intent.putExtra(FilePickerActivity.CONFIGS , new Configurations.Builder()
                .setCheckPermission(true)
                .setShowFiles(true)
                .setShowImages(false)
                .setShowVideos(false)
                .setMaxSelection(1)
                .setSuffixes("xlsx")
                .setSkipZeroSizeFiles(true)
                .build());

        startActivityForResult(intent, 102);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(
                    FilePickerActivity.MEDIA_FILES
            );

            String path = mediaFiles.get(0).getPath();
            mLoadingBar.show();
            if (requestCode == 102) {
                Observable.fromCallable((() -> {

                    onReadClick(path);

                    return true;
                })).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull Boolean aBoolean) {
                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                mLoadingBar.dismiss();
                            }
                        });
            }
        }
    }

    private void updateStudent(String id, String name, String phone, String idschedule) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Bug", error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("codestudent", id);
                params.put("name", name);
                params.put("phone", phone);
                params.put("sex", "true");
                params.put("baseclass", baseClass);
                params.put("status", "0");
                params.put("urlavatar", "");
                params.put("urlattend", "");
                params.put("schedule", idschedule);
                params.put("attendance", "");

                return params;
            }
        };

        requestQueue.add(request);
    }

    private void updateSchdedule(String subject, String timestart, String timesend,
                                 String room, String serial, String total) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, urlschedule,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        idchedule = response.trim();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Bug", error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("subject", subject);
                params.put("timestart", timestart);
                params.put("timeend", timesend);
                params.put("room", room);
                params.put("serial", serial);
                params.put("total", total);
                params.put("account", Prevalent.currentOnlineUser.getId());
                return params;
            }
        };

        requestQueue.add(request);
    }


    public void onReadClick(String path) {
        try {
            File input = new File(path);
            InputStream stream = new FileInputStream(input);

            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (int r = 7; r<55; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                String value = getCellAsString(row, 1, formulaEvaluator);
                String value2 = getCellAsString(row, 2, formulaEvaluator);
                String value3 = getCellAsString(row, 3, formulaEvaluator);

                updateStudent(value, value2, value3, idchedule);

            }
        } catch (Exception e) {
            /* proper exception handling to be here */
        }
    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
        }
        return value;
    }


}