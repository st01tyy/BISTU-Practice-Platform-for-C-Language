package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bistupracticeplatformforclanguage.LoginActivity;
import com.example.bistupracticeplatformforclanguage.module.LoginForm;
import com.example.bistupracticeplatformforclanguage.module.Student;

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
            Response response = okHttpClient.newCall(request).execute();
            Log.d("LoginTask", response.body().string());   //输出服务器返回的结果
        }
        catch(Exception e)
        {
            Log.e("LoginTask", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Student student)
    {
        progressDialog.dismiss();
    }
}
