package com.example.plb.ui.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.plb.R;
import com.example.plb.model.Schedule;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.classroom.ClassRoomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodayFragment extends Fragment {

    private final String url = "http://103.151.123.96:8000/schedule/";
    private int check = 0;

    private RecyclerView mScheduleRecyclerView;
    private List<Schedule> mScheduleList;
    private ScheduleAdapter mScheduleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScheduleRecyclerView = view.findViewById(R.id.todayRecyclerview);

        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleList = new ArrayList<>();
        mScheduleAdapter = new ScheduleAdapter(mScheduleList);
        mScheduleRecyclerView.setAdapter(mScheduleAdapter);

        mScheduleList.clear();
        getSchedule(url);

        mScheduleAdapter.setOnClickListener(new ScheduleAdapter.OnClickListener() {
            @Override
            public void onClick(Schedule schedule, int position) {
                Intent intent = new Intent(getActivity(), ClassRoomActivity.class);
                intent.putExtra("class", schedule.getId());
                intent.putExtra("subject", schedule.getSubject());
                intent.putExtra("codeclass", schedule.getCodeclass());
                intent.putExtra("total", schedule.getTotal());
                startActivity(intent);
            }
        });
    }


    public void getSchedule(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Date now = new Date();
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(now);
//                int serial = calendar.get(Calendar.DAY_OF_WEEK);

        int serial = 2;

        String id = Prevalent.currentOnlineUser.getId();

        String link = url + id + "&" + serial + "/";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);
                        mScheduleList.add(new Schedule(
                                explrObject.getString("id"),
                                explrObject.getString("subject"),
                                explrObject.getString("codeclass"),
                                explrObject.getString("timestart"),
                                explrObject.getString("timeend"),
                                explrObject.getString("room"),
                                explrObject.getString("serial"),
                                explrObject.getString("total"),
                                explrObject.getString("account")
                        ));
                    }

                    mScheduleAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TBug", error.toString());
            }
        });

        requestQueue.add(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (check == 0) {
            check++;
        } else {
            mScheduleList.clear();
            getSchedule(url);
        }

    }
}