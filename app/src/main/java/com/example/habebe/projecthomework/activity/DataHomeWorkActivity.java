package com.example.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habebe.projecthomework.adapter.DataHomeworkAdapter;
import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.dao.DataHomeworkCollectionDao;
import com.example.habebe.projecthomework.manager.NetworkConnection;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataHomeWorkActivity extends AppCompatActivity {

    ProgressDialog dialog;
    private DataHomeworkCollectionDao dao;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datahomework);
        initInstances();
    }

    private void requestDataHomeWork() {

        String id = getSharedPreferences("login", 1).getString("id", null);

        if(!NetworkConnection.isAvailable(DataHomeWorkActivity.this)){
            Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url("http://27.254.63.25/smartHomework/services/showdatahomework.php")
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Failed", Toast.LENGTH_SHORT).show();
                        if(dialog.isShowing()) dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();
                            Log.d("result", result);
                            Gson gson = new Gson();
                            dao = gson.fromJson(result, DataHomeworkCollectionDao.class);
                            listView.setAdapter(new DataHomeworkAdapter(DataHomeWorkActivity.this, dao.getData()));
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final AlertDialog alertDialog =
                                            new AlertDialog.Builder(DataHomeWorkActivity.this).create();
                                    View convertView = getLayoutInflater().inflate(R.layout.datahomework, null);
                                    alertDialog.setView(convertView);
                                    alertDialog.setTitle(dao.getData().get(position).getName());
                                    TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
                                    TextView txtChapter = (TextView) convertView.findViewById(R.id.txtChapter);
                                    TextView txtNoSet = (TextView) convertView.findViewById(R.id.txtNoset);
                                    TextView txtDetail = (TextView) convertView.findViewById(R.id.txtDetail);
                                    TextView txtExpDate = (TextView) convertView.findViewById(R.id.txtExpSent);

                                    txtName.setText("Course : "+dao.getData().get(position).getName());
                                    txtChapter.setText("Chapter :"+dao.getData().get(position).getChapter());
                                    txtNoSet.setText("No Set :"+dao.getData().get(position).getNoSet());
                                    txtDetail.setText("Detail :"+dao.getData().get(position).getDetail());
                                    txtExpDate.setText("Exp Date :"+dao.getData().get(position).getExpSent());

                                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                    alertDialog.show();
                                }
                            });
                            if(dialog.isShowing()) dialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        if(dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading. . .");
        }
        dialog.show();
    }

    private void initInstances() {
        listView = (ListView) findViewById(R.id.listView1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            case R.id.action_add :
                String status = getSharedPreferences("login", 1).getString("status", null);
                if(status == null) {
                    return true;
                } else if(status.equals("teacher")){
                    startActivity(new Intent(this, AddHomeworkActivity.class));
                } else {
                    Toast.makeText(DataHomeWorkActivity.this,
                            "ไม่สามารถเข้าใช้งานได้", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestDataHomeWork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }



}


