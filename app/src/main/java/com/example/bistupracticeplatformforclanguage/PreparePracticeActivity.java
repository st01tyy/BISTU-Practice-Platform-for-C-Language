package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bistupracticeplatformforclanguage.adapter.StageAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PreparePracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_practice);

        //设置按钮事件反应器
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //获取所有阶段名
        List<String> list = findStage();

        //初始化recyclerview
        RecyclerView stageList = (RecyclerView) findViewById(R.id.stage_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        StageAdapter stageAdapter = new StageAdapter(list, this);
        stageList.setLayoutManager(linearLayoutManager);
        stageList.setAdapter(stageAdapter);
    }

    private List<String> findStage()
    {
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

    private int getPriority(String str)
    {
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

}
