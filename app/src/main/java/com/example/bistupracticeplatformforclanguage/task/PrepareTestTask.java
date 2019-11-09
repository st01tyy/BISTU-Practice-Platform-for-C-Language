package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.Function;
import com.example.bistupracticeplatformforclanguage.PrepareTestActivity;
import com.example.bistupracticeplatformforclanguage.SelfTestActivity;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PrepareTestTask extends AsyncTask<Void, Integer, List<Object>>
{
    private PrepareTestActivity activity;
    private List<String> stageList;
    private int nQuestion;

    private ProgressDialog progressDialog;

    public PrepareTestTask(PrepareTestActivity activity, List<String> stageList)
    {
        this.activity = activity;
        this.stageList = stageList;
        this.nQuestion = 0;
    }

    public PrepareTestTask(PrepareTestActivity activity, List<String> stageList, int nQuestion)
    {
        this(activity, stageList);
        this.nQuestion = nQuestion;
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
            if(nQuestion > 0)
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
                if(totalQuestion.size() <= nQuestion)
                    questionList = totalQuestion;
                else
                {
                    Collections.shuffle(totalQuestion);
                    for(int i = 0; i < nQuestion; i++)
                    {
                        questionList.add(totalQuestion.get(i));
                    }
                }
                return questionList;
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
            Log.e("PrepareTestTask", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Object> objects)
    {
        super.onPostExecute(objects);
        progressDialog.dismiss();
        if(objects == null)
            Toast.makeText(activity, "自测准备时出错，请重试", Toast.LENGTH_LONG);
        else
        {
            Intent intent = new Intent(activity, SelfTestActivity.class);

            String title = null;
            if(nQuestion == 0)
                title = stageList.get(0) + "测试";
            else
                title = "随机测试";

            List<String> tStageList = new ArrayList<>();
            if(nQuestion == 0)
            {
                tStageList = stageList;
            }
            else
            {
                Set<String> set = new TreeSet<>();
                for(Object o : objects)
                {
                    if(o instanceof TrueFalseQuestion)
                    {
                        TrueFalseQuestion question = (TrueFalseQuestion) o;
                        set.add(question.getStage());
                    }
                    else
                    {
                        MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
                        set.add(question.getStage());
                    }
                }
                for(String str : set)
                {
                    tStageList.add(str);
                }
            }

            intent.putExtra("title", title);
            intent.putExtra("stageList", (Serializable) tStageList);
            intent.putExtra("questionList", (Serializable) objects);
            activity.startActivity(intent);
            //activity.finish();
        }
    }

}
