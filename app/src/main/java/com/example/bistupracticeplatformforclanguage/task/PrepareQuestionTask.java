package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bistupracticeplatformforclanguage.Function;
import com.example.bistupracticeplatformforclanguage.PrepareTestActivity;

import java.util.ArrayList;
import java.util.List;

public class PrepareQuestionTask extends AsyncTask<Void, Integer, List<Object>>
{
    private PrepareTestActivity activity;
    private List<String> stageList;
    private Type type;

    private ProgressDialog progressDialog;

    public enum Type
    {
        RANDOM, STAGE;
    }

    public PrepareQuestionTask(PrepareTestActivity activity, List<String> stageList, Type type)
    {
        this.activity = activity;
        this.stageList = stageList;
        this.type = type;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("准备中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected List<Object> doInBackground(Void... voids)
    {
        try
        {
            List<Object> questionList = new ArrayList<>();
            if(type == Type.RANDOM)
            {
                List<Object> totalQuestion = new ArrayList<>();
                for(String stage : stageList)
                {
                    List<Object> temp = Function.findQuestionByStage(stage);
                    for(Object o : temp)
                    {
                        totalQuestion.add(o);
                    }
                }
                return null;

            }
            else
            {
                for(String stage : stageList)
                {
                    List<Object> t = Function.findQuestionByStage(stage);
                    for(Object o : t)
                    {
                        questionList.add(o);
                    }
                }
                return questionList;
            }
        }
        catch(Exception e)
        {
            Log.e("PrepareQuestionTask", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Object> objects)
    {
        super.onPostExecute(objects);
    }
}
