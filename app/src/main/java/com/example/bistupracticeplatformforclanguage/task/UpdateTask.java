package com.example.bistupracticeplatformforclanguage.task;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.MainActivity;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.QuestionPrototype;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class UpdateTask extends AsyncTask<List<QuestionPrototype>, Integer, Boolean>
{
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    public UpdateTask(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        this.progressDialog = null;
    }


    @Override
    protected void onPreExecute()
    {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle("正在更新题库...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(List<QuestionPrototype>... lists)
    {
        try
        {
            //创建数据库
            SQLiteDatabase database = LitePal.getDatabase();

            List<Integer> blackList = new ArrayList<>();

            for(QuestionPrototype questionPrototype : lists[0])
            {
                //Log.d("UpdateTask", questionPrototype.getQuestionName());
                if(questionPrototype.getAnswer().equals("0") || questionPrototype.getAnswer().equals("1"))  //判断题
                {
                    TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion();
                    trueFalseQuestion.setAnswer(questionPrototype.getAnswer());
                    trueFalseQuestion.setDescription(questionPrototype.getQuestionName());
                    trueFalseQuestion.setDifficulty(questionPrototype.getDifficulty());
                    trueFalseQuestion.setQuestionId(Integer.parseInt(questionPrototype.getId()));
                    trueFalseQuestion.setStage(questionPrototype.getStage());
                    //更新至数据库
                    trueFalseQuestion.saveOrUpdate("description=?", trueFalseQuestion.getDescription());
                }
                else //选择题
                {

                    try
                    {
                        //解析题目内容
                        String str = questionPrototype.getQuestionDetails();
                        Document document = Jsoup.parse(str);
                        Elements elements = document.select("p");
                        StringBuffer sb = new StringBuffer("");
                        for(Element element : elements)
                            sb.append(element.text() + "\n");
                        String[] arr = function(sb.toString());

                        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
                        multipleChoiceQuestion.setAnswer(questionPrototype.getAnswer());
                        multipleChoiceQuestion.setDescription(sb.toString().substring(0, Integer.parseInt(arr[4])));
                        multipleChoiceQuestion.setDifficulty(questionPrototype.getDifficulty());
                        multipleChoiceQuestion.setQuestionId(Integer.parseInt(questionPrototype.getId()));
                        multipleChoiceQuestion.setStage(questionPrototype.getStage());
                        multipleChoiceQuestion.setSelectionA(arr[0]);
                        multipleChoiceQuestion.setSelectionB(arr[1]);
                        multipleChoiceQuestion.setSelectionC(arr[2]);
                        multipleChoiceQuestion.setSelectionD(arr[3]);

                        //更新至数据库
                        multipleChoiceQuestion.saveOrUpdate("description=?", multipleChoiceQuestion.getDescription());
                    }
                    catch(Exception e)
                    {
                        Log.w("UpdateTask", e.getMessage());
                        blackList.add(Integer.parseInt(questionPrototype.getId()));
                    }

                }
            }
            Log.d("UpdateTask", "length of black list： " +  blackList.size());
            return true;
        }
        catch(Exception e)
        {
            Log.w("UpdateTask", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        progressDialog.dismiss();
        if(aBoolean)
            Toast.makeText(mainActivity, "更新成功", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mainActivity, "更新失败", Toast.LENGTH_LONG).show();
    }

    private String[] function(String text)
    {
        String[] arr = new String[5];
        int a = 0, b = 0, c = 0, d = 0;
        for(int i = 0; i < text.length() -  1; i++)
        {
            if(text.charAt(i) == 'A' && (text.charAt(i + 1) == ')' || text.charAt(i + 1) == '）' || text.charAt(i + 1) == '．'))
            {
                a = i;
                arr[4] = Integer.toString(a);
                System.out.println(a);
            }
            if(text.charAt(i) == 'B' && (text.charAt(i + 1) == ')' || text.charAt(i + 1) == '）' || text.charAt(i + 1) == '．'))
                b = i;
            if(text.charAt(i) == 'C' && (text.charAt(i + 1) == ')' || text.charAt(i + 1) == '）' || text.charAt(i + 1) == '．'))
                c = i;
            if(text.charAt(i) == 'D' && (text.charAt(i + 1) == ')' || text.charAt(i + 1) == '）' || text.charAt(i + 1) == '．'))
                d = i;
        }
        arr[0] = text.substring(a + 2, b);
        arr[1] = text.substring(b + 2, c);
        arr[2] = text.substring(c + 2, d);
        arr[3] = text.substring(d + 2);
        for(int i = 0; i < 4; i++)
        {
            for(int j = arr[i].length() - 1; j >=0; j--)
            {
                if(arr[i].charAt(j) != ' ' && arr[i].charAt(j) != '\n')
                {
                    arr[i] = arr[i].substring(0, j + 1);
                    break;
                }
            }
        }
        return arr;
    }
}
