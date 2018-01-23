package com.dev.mevur.baselocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<ResponseBody> {
    private Retrofit retrofit;
    private Call<ResponseBody> call;
    private ListView log;
    private List<LogMessage> logMessages;
    private LogAdapter adapter;
    private BaseInfoService service;
    private LogMessage currentBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                   .baseUrl("http://api.cellocation.com:81")
                   .build();
        service = retrofit.create(BaseInfoService.class);
        log = findViewById(R.id.list_log);
        logMessages = new ArrayList<>();
        adapter = new LogAdapter(getApplicationContext(), logMessages);
        log.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refresh(null);

    }

    public void refresh(View view) {
        BaseStation bs = new BaseStation(getApplicationContext());
        currentBase = bs.getCurrentBaseStation();
        call = service.getBaseStationInfo(currentBase.getMcc(), currentBase.getMnc(),
                                          currentBase.getLac(), currentBase.getCi());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {

            if (null != response.body()) {
                String rep = response.body().string();
                rep = "location info:" + rep;
                currentBase.setContent(currentBase.toString() + rep);
                logMessages.add(currentBase);
                adapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "网络服务错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "网络服务错误", Toast.LENGTH_SHORT).show();
    }
}
