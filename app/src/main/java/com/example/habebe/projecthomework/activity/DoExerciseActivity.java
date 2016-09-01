package com.example.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.adapter.DoExerciseAdapter;
import com.example.habebe.projecthomework.dao.ResponseDao;
import com.example.habebe.projecthomework.dao.homeworkset.HomeworkSetCollection;
import com.example.habebe.projecthomework.dao.homeworkset.Question;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DoExerciseActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog dialog;
    private HomeworkSetCollection dao;
    private List<Question> question = new ArrayList<>();
    Button btnSendQuestion;
    private DoExerciseAdapter adapter;
    private String dataHomeworkId;
    private int score, wrong, count;
    private ResponseDao responseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initInstances();
        getQuestion();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getQuestion() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String id = bundle.getString("id", "0");
        final String amount = bundle.getString("amount", "0");
        Toast.makeText(DoExerciseActivity.this, "Id : " + id + "\n Amount : " + amount,
                Toast.LENGTH_SHORT).show();

        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url("http://27.254.63.25/smartHomework/services/generatequestion.php")
                .build();

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                dao = gson.fromJson(response.body().string(), HomeworkSetCollection.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        dataHomeworkId = dao.getHomeWorkSet().get(0).getDataHomeworkId();
                        for (int i = 0; i < dao.getHomeWorkSet().size(); i++) {
                            for (int j = 0; j < dao.getHomeWorkSet().get(i).getQuestion().size(); j++) {
                                question.add(dao.getHomeWorkSet().get(i).getQuestion().get(j));
                            }
                        }
                        Random random = new Random();
                        for (int i = 0; i < Integer.valueOf(amount); i++) {
                            question.remove(random.nextInt(question.size() - 1));
                        }
                        adapter = new DoExerciseAdapter(question, DoExerciseActivity.this);
                        listView.setAdapter(adapter);
                    }
                });
            }
        });

        dialog = new ProgressDialog(DoExerciseActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    private void initInstances() {
        listView = (ListView) findViewById(R.id.listView);
        btnSendQuestion = (Button) findViewById(R.id.btnSendQuestion);
        btnSendQuestion.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnSendQuestion) {
                updateHistoryToServer();

            }
        }
    };

    private void updateHistoryToServer() {
        score = 1;
        count = 0;
        question = adapter.getQuestions();
        for (int i = 0; i < question.size(); i++) {
            if (question.get(i).getScore() == 1)
                count++;
        }
        wrong = question.size() - count;
        SharedPreferences pref = getSharedPreferences("login", 1);
        String id = pref.getString("id", "");

        Toast.makeText(getApplicationContext(), "Student Id : " + id
                + "\nDatahomeworkId : " + dataHomeworkId, Toast.LENGTH_SHORT).show();

        RequestBody body = new FormBody.Builder()
                .add("student", id)
                .add("datahomework", dataHomeworkId)
                .add("score", String.valueOf(score))
                .add("amount_c", String.valueOf(count))
                .add("amount_w", String.valueOf(wrong))
                .add("summary_score", String.valueOf(score * count))
                .build();

        final Request request = new Request.Builder()
                .post(body)
                .url("http://27.254.63.25/smartHomework/services/addhomeworkhistory.php")
                .build();

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                responseDao = gson.fromJson(response.body().string(), ResponseDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (responseDao.isSuccess()) {
                            Toast.makeText(DoExerciseActivity.this,
                                    responseDao.getMessage(), Toast.LENGTH_SHORT).show();
                            AlertDialog alertDialog =
                                    new AlertDialog.Builder(DoExerciseActivity.this).create();
                            View convertView = getLayoutInflater().inflate(R.layout.dialog_do_homework_success, null);
                            alertDialog.setView(convertView);

                            TextView tvCorrect = (TextView) convertView.findViewById(R.id.tvCorrect);
                            TextView tvWrong = (TextView) convertView.findViewById(R.id.tvWrong);
                            TextView tvSummaryScore = (TextView) convertView.findViewById(R.id.tvSummaryScore);

                            tvCorrect.setText("Correct : " + count);
                            tvWrong.setText("Wrong : " + wrong);
                            tvSummaryScore.setText("Summary Score : " + (count * score));

                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

                            alertDialog.show();
                        } else {
                            Toast.makeText(DoExerciseActivity.this,
                                    responseDao.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        if (dialog == null) {
            dialog = new ProgressDialog(DoExerciseActivity.this);
            dialog.setMessage("loading..");
        }
        dialog.show();
    }
}
