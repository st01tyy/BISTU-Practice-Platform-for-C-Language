package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.MainActivity;
import com.example.bistupracticeplatformforclanguage.module.QuestionPrototype;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<Void, Integer, List<QuestionPrototype>>
{
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    public DownloadTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.progressDialog = null;
    }

    @Override
    protected void onPreExecute()
    {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("正在更新题库");
        progressDialog.show();
    }

    @Override
    protected List<QuestionPrototype> doInBackground(Void... voids)
    {
        try
        {
            //下载选择题题库
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://211.68.35.79:7777/android/getQuestion?questionType=%E9%80%89%E6%8B%A9%E9%A2%98")
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Log.d("DownloadTask", json);

            //解析JSON
            List<QuestionPrototype> result = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            List<QuestionPrototype> listA = gson.fromJson(jsonObject.getString("data"), new TypeToken<List<QuestionPrototype>>(){}.getType());
            if(listA != null)
            {
                Log.d("DownloadTask", Integer.toString(listA.size()));
                for(QuestionPrototype prototype : listA)
                    result.add(prototype);
            }

            //下载判断题题库
            client = new OkHttpClient();
            request = new Request.Builder()
                    .url("http://211.68.35.79:7777/android/getQuestion?questionType=%E5%88%A4%E6%96%AD%E9%A2%98")
                    .build();
            response = client.newCall(request).execute();
            json = response.body().string();
            Log.d("DownloadTask", json);

            //解析JSON
            jsonObject = new JSONObject(json);
            gson = new Gson();
            List<QuestionPrototype> listB = gson.fromJson(jsonObject.getString("data"), new TypeToken<List<QuestionPrototype>>(){}.getType());
            if(listB != null)
            {
                Log.d("DownloadTask", Integer.toString(listB.size()));
                for(QuestionPrototype prototype : listB)
                    result.add(prototype);
            }
            return result;
        }
        catch(Exception e)
        {
            Log.e("DownloadTask", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<QuestionPrototype> questionPrototypes)
    {
        progressDialog.dismiss();
        if(questionPrototypes == null || questionPrototypes.size() == 0)
            Toast.makeText(mainActivity, "更新题库失败，请重试", Toast.LENGTH_LONG).show();
        else
        {

        }
    }
}
