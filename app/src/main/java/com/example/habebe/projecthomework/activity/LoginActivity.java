package com.example.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.dao.LoginResponseDao;
import com.example.habebe.projecthomework.manager.NetworkConnection;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tvWelcome;
    EditText edtUsername;
    EditText edtPassword;
    Button btnSubmit;
    private GoogleApiClient client;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkUserSession();

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "โปรดกรอก username และ password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!NetworkConnection.isAvailable(LoginActivity.this)) {
                    Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestBody body = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();


                Request request = new Request.Builder()
                        .post(body)
                        .url("http://27.254.63.25/smartHomework/services/login.php")
                        .build();

                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("กำลังโหลดข้อมูล");
                dialog.show();

                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String result;
                                try {
                                    result = response.body().string();
                                    Gson gson = new Gson();
                                    LoginResponseDao dao = gson.fromJson(result, LoginResponseDao.class);
                                    if (dao.isSuccess()) {
                                        createUserSession(dao);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), dao.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                    Log.d("result", result);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void checkUserSession() {
        SharedPreferences pref = getSharedPreferences("login", 1);
        if (pref.getBoolean("login_flag", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void createUserSession(LoginResponseDao dao) {
        SharedPreferences pref = getSharedPreferences("login", 1);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("login_flag", true);
        edit.putString("id", dao.getId());
        edit.putString("username", dao.getUsername());
        edit.putString("status", dao.getStatus());
        edit.apply();
    }
}