package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.adapter.StageAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class ChooseStageActivity extends AppCompatActivity
{
    private String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stage);

        //获取跳转目标类型 若type为空，则跳转至练习；若type为自测，跳转至自测
        type = getIntent().getStringExtra("type");

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

        //设置标题
        TextView text_title = (TextView) findViewById(R.id.text_question_activity_title);
        if(type != null && type.equals("自测"))
            text_title.setText("选择自测阶段");

        //初始化recyclerview
        RecyclerView stageList = (RecyclerView) findViewById(R.id.stage_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        StageAdapter stageAdapter = null;
        if(type != null && type.equals("自测"))
            stageAdapter = new StageAdapter(list, this, type);
        else
            stageAdapter = new StageAdapter(list, this);
        stageList.setLayoutManager(linearLayoutManager);
        stageList.setAdapter(stageAdapter);
    }

}
