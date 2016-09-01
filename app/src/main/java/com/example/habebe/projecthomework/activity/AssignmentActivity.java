package com.example.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.adapter.DataHomeworkAdapter;
import com.example.habebe.projecthomework.dao.DataHomeworkCollectionDao;
import com.example.habebe.projecthomework.dao.DataHomeworkItemDao;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AssignmentActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog dialog;
    private DataHomeworkCollectionDao dao;
    private DataHomeworkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initInstances();
    }

    private void initInstances() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new DataHomeworkAdapter(AssignmentActivity.this,
                new ArrayList<DataHomeworkItemDao>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AssignmentActivity.this, DoExerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", dao.getData().get(position).getId());
                bundle.putString("amount", dao.getData().get(position).getAmount());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                finish();
                Log.d("finish", "success");
            }
        });
    }

    private void getData(){
        String id = getSharedPreferences("login", 1).getString("id", null);
        if(id == null){
            Toast.makeText(AssignmentActivity.this, "null", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog = new ProgressDialog(AssignmentActivity.this);
        dialog.setMessage("loading...");
        dialog.show();
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://27.254.63.25/smartHomework/services/showdatahomework.php")
                .build();

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dialog.isShowing()) dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                dao = gson.fromJson(response.body().string(), DataHomeworkCollectionDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dialog.isShowing()) dialog.dismiss();
                        adapter.setData(dao.getData());
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}