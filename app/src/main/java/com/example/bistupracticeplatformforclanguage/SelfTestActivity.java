package com.example.bistupracticeplatformforclanguage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

public class SelfTestActivity extends AnswerQuestionActivity
{
    private List<String> stageList; //阶段列表
    private List<Object> questionList;  //题目列表
    private String[] ans;   //答案列表
    private int position;   //当前位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);
    }

    @Override
    public void updateAnswer(String ans) {

    }

    @Override
    public void onBackPressed()
    {
        //确认退出对话框
        new AlertDialog.Builder(this).setTitle("确定要终止自测吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("继续测试", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                }).show();
    }
}
