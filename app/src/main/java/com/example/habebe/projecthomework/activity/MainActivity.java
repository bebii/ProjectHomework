package com.example.habebe.projecthomework.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.habebe.projecthomework.R;

public class MainActivity extends AppCompatActivity {

    ImageView imgVwDataHw, imgAssign, imgVwScore, imgExam, imgHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imgVwDataHw = (ImageView) findViewById(R.id.imgVwDataHw);
        imgAssign = (ImageView) findViewById(R.id.imgAssign);
        imgVwScore = (ImageView) findViewById(R.id.imgVwScore);
        imgExam = (ImageView) findViewById(R.id.imgExam);
        imgHistory =(ImageView) findViewById(R.id.imgHistory);

        imgVwDataHw.setOnClickListener(onClickListener);
        imgAssign.setOnClickListener(onClickListener);
        imgVwScore.setOnClickListener(onClickListener);
        imgExam.setOnClickListener(onClickListener);
        imgHistory.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == imgVwDataHw){
                startActivity(new Intent(MainActivity.this,
                        DataHomeWorkActivity.class));
            } else if (v == imgAssign) {
                String string = getSharedPreferences("login", 1).getString("status", null);
                if(string == null)
                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                else if(string.equals("student")){
                    startActivity(new Intent(MainActivity.this,
                            AssignmentActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "ไม่สามารถเข้าได้", Toast.LENGTH_SHORT).show();
                }

            } else if (v == imgVwScore) {
                startActivity(new Intent(MainActivity.this,
                        ShowScoreActivity.class));
            } else if (v == imgExam) {
                startActivity(new Intent(MainActivity.this,
                        ScheduleExamActivity.class));
            } else if (v == imgHistory){
                startActivity(new Intent(MainActivity.this,
                        HistoryActivity.class));
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_logout :
                destroySession();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void destroySession(){
        SharedPreferences pref = getSharedPreferences("login", 1);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.apply();
        startActivity(new Intent(MainActivity.this,
                LoginActivity.class));
        finish();
    }
}
