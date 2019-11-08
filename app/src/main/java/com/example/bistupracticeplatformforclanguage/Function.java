package com.example.bistupracticeplatformforclanguage;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Function
{
    /*
    存储公共函数的静态类
     */

    public static List<String> findStage()
    {
        /*
        获取当前数据库中所有的阶段名
         */

        //分别查询两个表
        Cursor cursor = LitePal.findBySQL("SELECT stage FROM multiplechoicequestion GROUP BY stage");
        Set<String> set = new TreeSet<>();  //使用集合判重
        if(cursor.moveToFirst())
        {
            for(int i = 0; i < cursor.getCount(); i++)
            {
                Log.d("PreparePracticeActivity", cursor.getString(0));
                set.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        cursor = LitePal.findBySQL("SELECT stage FROM truefalsequestion GROUP BY stage");
        if(cursor.moveToFirst())
        {
            for(int i = 0; i < cursor.getCount(); i++)
            {
                Log.d("PreparePracticeActivity", cursor.getString(0));
                set.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }

        //整理查询结果
        List<String> list = new ArrayList<>();
        for(String str : set)
        {
            list.add(str);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(new Comparator<String>()
            {
                @Override
                public int compare(String s, String t1)
                {
                    if(getPriority(s) < getPriority(t1))
                        return -1;
                    else if(getPriority(s) == getPriority(t1))
                        return 0;
                    else
                        return 1;
                }
            });
            Log.d("PreparePracticeActivity", "sorted");
        }
        Log.d("PreparePracticeActivity", Integer.toString(list.size()));
        return list;
    }

    private static int getPriority(String str)
    {
        /*
        获取阶段名对应的优先级，排序用
         */

        if(str.equals("阶段一"))
            return 1;
        else if(str.equals("阶段二"))
            return 2;
        else if(str.equals("阶段三"))
            return 3;
        else if(str.equals("阶段四"))
            return 4;
        else if(str.equals("阶段五"))
            return 5;
        else if(str.equals("阶段六"))
            return 6;
        else if(str.equals("阶段七"))
            return 7;
        else
            return 8;
    }

    public static List<Object> findQuestionByStage(String stage)
    {
        List<Object> list = new ArrayList<>();

        //获取选择题题目列表
        long id = 1;
        MultipleChoiceQuestion multipleChoiceQuestion = LitePal.find(MultipleChoiceQuestion.class, id);
        while(multipleChoiceQuestion != null)
        {
            if(multipleChoiceQuestion.getStage().equals(stage))
                list.add(multipleChoiceQuestion);
            id++;
            multipleChoiceQuestion = LitePal.find(MultipleChoiceQuestion.class, id);
        }

        //获取判断题列表
        id = 1;
        TrueFalseQuestion trueFalseQuestion = LitePal.find(TrueFalseQuestion.class, id);
        while(trueFalseQuestion != null)
        {
            if(trueFalseQuestion.getStage().equals(stage))
                list.add(trueFalseQuestion);
            id++;
            trueFalseQuestion = LitePal.find(TrueFalseQuestion.class, id);
        }

        Log.d("PracticeActivity", "题目列表长度：" + Integer.toString(list.size()));
        return list;
    }


}
