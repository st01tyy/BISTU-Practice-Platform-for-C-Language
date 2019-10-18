package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.LoginActivity;
import com.example.bistupracticeplatformforclanguage.MainActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.LoginForm;
import com.example.bistupracticeplatformforclanguage.module.Student;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginTask extends AsyncTask<LoginForm, Integer, Student>
{
    private LoginActivity loginActivity;
    private ProgressDialog progressDialog;

    public LoginTask(LoginActivity loginActivity)
    {
        this.loginActivity = loginActivity;
    }

    @Override
    protected void onPreExecute()
    {
        //显示载入画面
        progressDialog = new ProgressDialog(loginActivity);
        progressDialog.setMessage("正在登录");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Student doInBackground(LoginForm... loginForms)
    {
        Student student = null;
        try
        {
            //向服务器提交表单
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("id", loginForms[0].getId())
                    .add("pw", loginForms[0].getPw())
                    .build();
            Request request = new Request.Builder()
                    .url("http://211.68.35.79:7777/android/studentLogin")
                    .post(requestBody)
                    .build();

            //获取服务器返回的JSON
            Response response = okHttpClient.newCall(request).execute();
            String json = response.body().string();
            Log.d("LoginTask", json);   //输出服务器返回的结果
            JSONObject jsonObject = new JSONObject(json);

            //解析JSON
            if(jsonObject.getString("msg").equals("成功"))
            {
                Gson gson = new Gson();
                student = gson.fromJson(jsonObject.getString("data"), Student.class);
            }
        }
        catch(Exception e)
        {
            Log.e("LoginTask", e.getMessage());
            e.printStackTrace();
        }
        return student;
    }

    @Override
    protected void onPostExecute(Student student)
    {
        progressDialog.dismiss();

        //登陆失败
        if(student == null)
            loginActivity.onLoginFail();
        //登陆成功
        else
        {
            Intent intent = new Intent(loginActivity, MainActivity.class);
            intent.putExtra("student", student);
            loginActivity.startActivity(intent);
            loginActivity.finish();
        }
    }
}
