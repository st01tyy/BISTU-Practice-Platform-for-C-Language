package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.TestFinishActivity;
import com.example.bistupracticeplatformforclanguage.module.Mistake;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;

import java.util.List;

public class MistakeImportTask extends AsyncTask<Void, Integer, Boolean>
{
    private TestFinishActivity activity;
    private List<Object> mistakeList;

    private ProgressDialog progressDialog;

    public MistakeImportTask(TestFinishActivity activity, List<Object> mistakeList)
    {
        super();
        this.activity = activity;
        this.mistakeList = mistakeList;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("添加中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        try
        {
            for(Object o : mistakeList)
            {
                if(o instanceof TrueFalseQuestion)
                {
                    TrueFalseQuestion question = (TrueFalseQuestion) o;
                    Mistake mistake = new Mistake(0, question.getQuestionId());
                    mistake.saveOrUpdate("questionid=?",  Integer.toString(mistake.getQuestionId()));
                }
                else
                {
                    MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
                    Mistake mistake = new Mistake(1, question.getQuestionId());
                    mistake.saveOrUpdate("questionid=?",  Integer.toString(mistake.getQuestionId()));
                }
            }
            return true;
        }
        catch (Exception e)
        {
            Log.e("MistakeImportTask", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();

        if(aBoolean)
        {
            Toast.makeText(activity, "添加成功", Toast.LENGTH_LONG).show();
            List<Mistake> list = LitePal.findAll(Mistake.class);
            Log.d("MistakeImportTask",  "添加后数据库大小：" + Integer.toString(list.size()));
            activity.setAdded(true);
        }
        else
            Toast.makeText(activity, "添加失败", Toast.LENGTH_LONG).show();
    }
}
