package com.example.plb.ui.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OverallFragment extends Fragment {

    private final String url = "http://103.151.123.96:8000/schedule/";

    private RecyclerView mScheduleRecyclerView;
    private List<Schedule> mScheduleList;
    private ScheduleOverallAdapter mScheduleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScheduleRecyclerView = view.findViewById(R.id.overallRecyclerView);

        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleList = new ArrayList<>();
        mScheduleAdapter = new ScheduleOverallAdapter(mScheduleList);
        mScheduleRecyclerView.setAdapter(mScheduleAdapter);

        getSchedule(url);

        mScheduleAdapter.setOnClickListener(new ScheduleOverallAdapter.OnClickListener() {
            @Override
            public void onClick(Schedule schedule, int position) {
                Intent intent = new Intent(getActivity(), ClassRoomActivity.class);
                intent.putExtra("class", schedule.getId());
                intent.putExtra("codeclass", schedule.getCodeclass());
                intent.putExtra("subject", schedule.getSubject());
                intent.putExtra("total", schedule.getTotal());
                startActivity(intent);
            }
        });
    }

    public void getSchedule(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String id = Prevalent.currentOnlineUser.getId();

        String link = url + id + "&" + " /";

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
                Log.d("OveralBug", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                int serial = calendar.get(Calendar.DAY_OF_WEEK);

                String id = Prevalent.currentOnlineUser.getId();

                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("serial", "");
                return params;
            }
        };

        requestQueue.add(request);
    }

}