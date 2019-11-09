package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.SelfTestActivity;
import com.example.bistupracticeplatformforclanguage.TestFinishActivity;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.List;

public class JudgeTestTask extends AsyncTask<Void, Integer, List<Object>>
{
    private SelfTestActivity activity;
    private String title;
    private List<String> stageList;
    private List<Object> questionList;
    private String[] ans;

    private ProgressDialog progressDialog;
    private List<String> knowledgeList;

    public JudgeTestTask(SelfTestActivity activity, String title, List<String> stageList, List<Object> questionList, String[] ans)
    {
        this.activity = activity;
        this.title = title;
        this.stageList = stageList;
        this.questionList = questionList;
        this.ans = ans;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        knowledgeList = new ArrayList<>();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("判题中...");
        progressDialog.show();
    }

    @Override
    protected List<Object> doInBackground(Void... voids)
    {
       try
       {
           List<Object> mistakeList = new ArrayList<>();
           for(int i = 0; i < ans.length; i++)
           {
               Object o = questionList.get(i);
               if(o instanceof TrueFalseQuestion)
               {
                   TrueFalseQuestion question = (TrueFalseQuestion) o;
                   if(ans[i] != null && ans[i].equals(question.getAnswer()));
                   else
                       mistakeList.add(o);
               }
               else
               {
                   MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
                   if(ans[i] != null && ans[i].equals(question.getAnswer()));
                   else
                       mistakeList.add(o);
               }
           }
           for(String str : stageList)
           {

           }
           return mistakeList;
       }
       catch(Exception e)
       {
           Log.e("JudgeTestTask", e.getMessage());
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
            Toast.makeText(activity, "判题时发生错误", Toast.LENGTH_LONG);
        else
        {
            Intent intent = new Intent(activity, TestFinishActivity.class);
            intent.putExtra("title", title);
        }
    }
}
