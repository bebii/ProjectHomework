package com.example.habebe.projecthomework.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.adapter.CourseAdapter;
import com.example.habebe.projecthomework.adapter.TeacherAdapter;
import com.example.habebe.projecthomework.dao.ChapterAdapter;
import com.example.habebe.projecthomework.dao.ChapterCollectionDao;
import com.example.habebe.projecthomework.dao.CourseCollectionDao;
import com.example.habebe.projecthomework.dao.ResponseDao;
import com.example.habebe.projecthomework.dao.TeacherCollectionDao;
import com.example.habebe.projecthomework.manager.NetworkConnection;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddHomeworkActivity extends AppCompatActivity {

    ProgressDialog dialog;
    EditText edtCourse, edtChapter, edtNoSet,
            edtTeacher, edtDetail, edtAddDate, edtExpDate;
    EditText edtAmount;
    String chCourse, chTeacher,chChapter;
    Calendar calendar;

    private CourseCollectionDao courseDao;
    private TeacherCollectionDao teacher;
    private GregorianCalendar dateSelected;
    private ChapterCollectionDao chapterCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        edtCourse = (EditText) findViewById(R.id.edtCourse);
        edtChapter = (EditText) findViewById(R.id.edtChapter);
        edtNoSet = (EditText) findViewById(R.id.edtNoSet);
        edtTeacher = (EditText) findViewById(R.id.edtTeacher);
        edtDetail = (EditText) findViewById(R.id.edtDetail);
        edtAddDate = (EditText) findViewById(R.id.edtAddDate);
        edtExpDate = (EditText) findViewById(R.id.edtExpDate);
        edtAmount = (EditText) findViewById(R.id.edtAmount);

        edtCourse.setOnClickListener(onClickListener);
        edtCourse.setOnFocusChangeListener(focusChangeListener);
        edtTeacher.setOnClickListener(onClickListener);
        edtTeacher.setOnFocusChangeListener(focusChangeListener);
        edtAddDate.setOnFocusChangeListener(focusChangeListener);
        edtChapter.setOnClickListener(onClickListener);
        edtChapter.setOnFocusChangeListener(focusChangeListener);
        edtAddDate.setOnClickListener(onClickListener);
        edtExpDate.setOnFocusChangeListener(focusChangeListener);
        edtExpDate.setOnClickListener(onClickListener);

    }

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v == edtCourse && hasFocus) {
                showCourse();
            } else if (v == edtChapter && hasFocus){
                showChapter();
            } else if (v == edtTeacher && hasFocus) {
                showTeacher();
            } else if (v == edtAddDate && hasFocus) {
                getAddDate();
            } else if (v == edtExpDate && hasFocus) {
                getExpDate();
            }

        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == edtCourse) {
                showCourse();
            } else if (v == edtChapter){
                showChapter();

            } else if (v == edtTeacher) {
                showTeacher();
            } else if (v == edtAddDate) {
                getAddDate();
            } else if (v == edtExpDate) {
                getExpDate();
            }
        }
    };

    private void getAddDate() {
        calendar = new GregorianCalendar();
        new DatePickerDialog(AddHomeworkActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar dateCurrent =
                        new GregorianCalendar(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateSelected =
                        new GregorianCalendar(year, monthOfYear, dayOfMonth);
                if (dateSelected.before(dateCurrent)) {
                    Toast.makeText(getApplicationContext(),
                            "เลือกวันที่ใหม่", Toast.LENGTH_SHORT).show();
                    edtAddDate.setText("");
                    return;
                }
                edtAddDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void getExpDate() {
        calendar = new GregorianCalendar();
        new DatePickerDialog(AddHomeworkActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar dateExp = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                if (dateExp.before(dateSelected)) {
                    Toast.makeText(getApplicationContext(),
                            "โปรดเลือกวันที่", Toast.LENGTH_SHORT).show();
                    edtExpDate.setText("");
                    return;
                }
                edtExpDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void showTeacher() {

        if (!NetworkConnection.isAvailable(AddHomeworkActivity.this)) {
            Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder()
                .url("http://27.254.63.25/smartHomework/services/showteacher.php")
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
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
                teacher = gson.fromJson(response.body().string(), TeacherCollectionDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        final AlertDialog alertDialog = new AlertDialog
                                .Builder(AddHomeworkActivity.this).create();
                        View convertView = getLayoutInflater().inflate(R.layout.listview, null);
                        alertDialog.setView(convertView);
                        alertDialog.setTitle("เลือกอาจารย์");
                        ListView lv = (ListView) convertView.findViewById(R.id.listView);
                        lv.setAdapter(new TeacherAdapter(AddHomeworkActivity.this,
                                teacher.getData()));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                edtTeacher.setText(teacher.getData().get(position).getFullName());
                                chTeacher = teacher.getData().get(position).getId();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        });

        if (dialog == null) {
            dialog = new ProgressDialog(AddHomeworkActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();

    }


    private void showCourse() {
        if (!NetworkConnection.isAvailable(AddHomeworkActivity.this)) {
            Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://27.254.63.25/smartHomework/services/showcourse.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
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
                courseDao = gson.fromJson(response.body().string(), CourseCollectionDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (courseDao.getData().size() < 0)
                            return;
                        Toast.makeText(AddHomeworkActivity.this, courseDao.getMessage(), Toast.LENGTH_SHORT).show();
                        final AlertDialog alertDialog = new AlertDialog.
                                Builder(AddHomeworkActivity.this).create();
                        View convertView = getLayoutInflater().inflate(R.layout.listview, null);
                        alertDialog.setView(convertView);
                        alertDialog.setTitle("เลือกวิชาเรียน");
                        ListView lv = (ListView) convertView.findViewById(R.id.listView);
                        lv.setAdapter(new CourseAdapter(AddHomeworkActivity.this, courseDao.getData()));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                edtCourse.setText(courseDao.getData().get(position).getName());
                                chCourse = courseDao.getData().get(position).getId();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        });

        if (dialog == null) {
            dialog = new ProgressDialog(AddHomeworkActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();


    }

    private void showChapter() {
        if (!NetworkConnection.isAvailable(AddHomeworkActivity.this)) {
            Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (chCourse == null) return;

        RequestBody body = new FormBody.Builder()
                .add("id", chCourse)
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(body)
                .url("http://27.254.63.25/smartHomework/services/showchapter.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
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
                String result = response.body().string();
                Log.d("result", result);
                Gson gson = new Gson();
                chapterCollection = gson.fromJson(result, ChapterCollectionDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        if(chapterCollection.getData().size() < 0 ) return;
                        final AlertDialog alertDialog = new AlertDialog.
                                Builder(AddHomeworkActivity.this).create();
                        View convertView = getLayoutInflater().inflate(R.layout.listview, null);
                        alertDialog.setView(convertView);
                        alertDialog.setTitle("เลือกเรื่อง");
                        ListView lv = (ListView) convertView.findViewById(R.id.listView);
                        lv.setAdapter(new ChapterAdapter(AddHomeworkActivity.this,chapterCollection.getData()));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                edtChapter.setText(chapterCollection.getData().get(position).getName());
                                chChapter = chapterCollection.getData().get(position).getId();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });

            }
        });

        if (dialog == null) {
            dialog = new ProgressDialog(AddHomeworkActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();


    }

    public void addHomework() {

        if (edtCourse.getText().toString().isEmpty() ||
                edtChapter.getText().toString().isEmpty() ||
                edtNoSet.getText().toString().isEmpty() ||
                edtTeacher.getText().toString().isEmpty() ||
                edtDetail.getText().toString().isEmpty() ||
                edtAmount.getText().toString().isEmpty() ||
                edtAddDate.getText().toString().isEmpty() ||
                edtExpDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "กรอกข้อมูล", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences pref = getSharedPreferences("login", 1);
        String username = pref.getString("username", "admin");

        RequestBody body = new FormBody.Builder()
                .add("course", chCourse)
                .add("chapter", edtChapter.getText().toString())
                .add("noset", edtNoSet.getText().toString())
                .add("teacher", chTeacher)
                .add("detail", edtDetail.getText().toString())
                .add("amount", edtAmount.getText().toString())
                .add("addby", username)
                .add("dateadd", edtAddDate.getText().toString())
                .add("expdate", edtExpDate.getText().toString())
                .build();


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://27.254.63.25/smartHomework/services/adddatahomework.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddHomeworkActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String result = response.body().string();
                Log.d("result", result);
                final ResponseDao dao = gson.fromJson(result, ResponseDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dao.isSuccess()) {
                            Toast.makeText(getApplicationContext(),
                                    dao.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    dao.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                addHomework();
                return true;
            case R.id.item_clear:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_homework, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
