package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.adapter.PracticeStageAdapter;

import java.util.List;

public class PreparePracticeActivity extends AppCompatActivity
{
    private String type = null;

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
        List<String> list = Function.findStage();

        //初始化recyclerview
        RecyclerView stageList = (RecyclerView) findViewById(R.id.stage_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        PracticeStageAdapter practiceStageAdapter = new PracticeStageAdapter(list, this);
        stageList.setLayoutManager(linearLayoutManager);
        stageList.setAdapter(practiceStageAdapter);
    }

}
